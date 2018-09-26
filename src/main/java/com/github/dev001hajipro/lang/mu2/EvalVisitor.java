package com.github.dev001hajipro.lang.mu2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.dev001hajipro.lang.mu.MuParser;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.AdditiveExprContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.AndExprContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.AssignmentContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.BooleanAtomContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.Condition_blockContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.EqualityExprContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.For_statContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.IdAtomContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.If_statContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.LogContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.MultiplicationExprContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.NilAtomContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.NotExprContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.NumberAtomContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.OrExprContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.ParExprContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.PowExprContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.RelationExprContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.StringAtomContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.UnaryMinusExprContext;
import com.github.dev001hajipro.lang.mu2.Mu2Parser.While_statContext;


public class EvalVisitor extends Mu2BaseVisitor<Value> {

	public static final double SMALL_VALUE = 0.00000000001;

	// グローバル変数の確保。
	private Map<String, Value> memory = new HashMap<>();

	//////////////////////////////////////////////////////
	// statement
	//////////////////////////////////////////////////////

	@Override
	public Value visitAssignment(AssignmentContext ctx) {
		String id = ctx.ID().getText();
		Value value = this.visit(ctx.expr());
		return memory.put(id, value);
	}
	
	@Override
	public Value visitIf_stat(If_statContext ctx) {
		List<Condition_blockContext> conditions = ctx.condition_block();
		boolean evaluateBlock = false;
		// condition_blockの評価
		for (Mu2Parser.Condition_blockContext condition : conditions) {
			// ここで複数のif else ifを評価して、trueなら、その先のブロックを
			// 処理する。
			// IF expr0 ELSE IF expr1 ELSE IF expr2 ....
			Value evaluated = this.visit(condition.expr());
			if (evaluated.asBoolean()) {
				// .g4のcondition_blockルールでexpr=true
				evaluateBlock = true;
				this.visit(condition.stat_block());
				break;
			}
		}
		// (ELSE stat_block)
		if (!evaluateBlock && ctx.stat_block() != null) {
			this.visit(ctx.stat_block());
		}
		return Value.VOID;
	}
	
	@Override
	public Value visitFor_stat(For_statContext ctx) {
		
		for (this.visit(ctx.assignment(0));
				this.visit(ctx.expr()).asBoolean();
					this.visit(ctx.assignment(1))
				) {
			this.visit(ctx.stat_block());
		}
		
		return Value.VOID;
	}
	
	@Override
	public Value visitWhile_stat(While_statContext ctx) {
		Value value = this.visit(ctx.expr());
		while (value.asBoolean()) {
			this.visit(ctx.stat_block());
			
			value = this.visit(ctx.expr());
		}
		
		return Value.VOID;
	}
	
	@Override
	public Value visitLog(LogContext ctx) {
		Value value = this.visit(ctx.expr());
		System.out.println(value);
		return value;
	}

	//////////////////////////////////////////////////////
	// expr
	//////////////////////////////////////////////////////

	@Override
	public Value visitPowExpr(PowExprContext ctx) {
		Value left = this.visit(ctx.expr(0));
		Value right = this.visit(ctx.expr(1));
		return new Value(Math.pow(left.asDouble(), right.asDouble()));
	}

	@Override
	public Value visitUnaryMinusExpr(UnaryMinusExprContext ctx) {
		Value value = this.visit(ctx.expr());
		return new Value(value.asDouble() * -1);
	}

	@Override
	public Value visitNotExpr(NotExprContext ctx) {
		Value value = this.visit(ctx.expr());
		return new Value(!value.asBoolean());
	}

	@Override
	public Value visitMultiplicationExpr(MultiplicationExprContext ctx) {
		Value left = this.visit(ctx.expr(0));
		Value right = this.visit(ctx.expr(1));

		switch (ctx.op.getType()) {
		case MuParser.MULT:
			return new Value(left.asDouble() * right.asDouble());
		case MuParser.DIV:
			return new Value(left.asDouble() / right.asDouble());
		case MuParser.MOD:
			return new Value(left.asDouble() % right.asDouble());
		default:
			throw new RuntimeException("unknon operator: " + MuParser.VOCABULARY.getLiteralName(ctx.op.getType()));
		}
	}

	@Override
	public Value visitAdditiveExpr(AdditiveExprContext ctx) {
		Value left = this.visit(ctx.expr(0));
		Value right = this.visit(ctx.expr(1));

		switch (ctx.op.getType()) {
		case MuParser.PLUS: // 文字列の加算もある
		{
			if (left.isDouble() && right.isDouble()) {
				return new Value(left.asDouble() + right.asDouble());
			}
			return new Value(left.asString() + right.asString());
		}

		case MuParser.MINUS:
			return new Value(left.asDouble() - right.asDouble());
		default:
			throw new RuntimeException("unknon operator: " + MuParser.VOCABULARY.getLiteralName(ctx.op.getType()));
		}
	}

	@Override
	public Value visitRelationExpr(RelationExprContext ctx) {
		Value left = this.visit(ctx.expr(0));
		Value right = this.visit(ctx.expr(1));

		switch (ctx.op.getType()) {
		case MuParser.LTEQ:
			return new Value(left.asDouble() <= right.asDouble());
		case MuParser.GTEQ:
			return new Value(left.asDouble() >= right.asDouble());
		case MuParser.LT:
			return new Value(left.asDouble() < right.asDouble());
		case MuParser.GT:
			return new Value(left.asDouble() > right.asDouble());
		default:
			throw new RuntimeException("unknon operator: " + MuParser.VOCABULARY.getLiteralName(ctx.op.getType()));
		}
	}

	@Override
	public Value visitEqualityExpr(EqualityExprContext ctx) {
		Value left = this.visit(ctx.expr(0));
		Value right = this.visit(ctx.expr(1));
		switch (ctx.op.getType()) {
		case MuParser.EQ:
			if (left.isDouble() && right.isDouble()) {
				return new Value(Math.abs(left.asDouble() - right.asDouble()) < SMALL_VALUE); 
			}
			return new Value(left.equals(right));
		case MuParser.NEQ:
			if (left.isDouble() && right.isDouble()) {
				return new Value(Math.abs(left.asDouble() - right.asDouble()) >= SMALL_VALUE); 
			}
			return new Value(!left.equals(right));
		default:
			throw new RuntimeException("unknon operator: " + MuParser.VOCABULARY.getLiteralName(ctx.op.getType()));
		}
	}
	
	@Override
	public Value visitAndExpr(AndExprContext ctx) {
		Value left = this.visit(ctx.expr(0));
		Value right = this.visit(ctx.expr(1));
		return new Value(left.asBoolean() && right.asBoolean());
	}
	
	@Override
	public Value visitOrExpr(OrExprContext ctx) {
		Value left = this.visit(ctx.expr(0));
		Value right = this.visit(ctx.expr(1));
		return new Value(left.asBoolean() || right.asBoolean());
	}
	
	//////////////////////////////////////////////////////
	// atom
	//////////////////////////////////////////////////////

	@Override
	public Value visitParExpr(ParExprContext ctx) {
		return this.visit(ctx.expr());
	}

	@Override
	public Value visitNumberAtom(NumberAtomContext ctx) {
		return new Value(Double.valueOf(ctx.getText()));
	}

	@Override
	public Value visitBooleanAtom(BooleanAtomContext ctx) {
		return new Value(Boolean.valueOf(ctx.getText()));
	}

	@Override
	public Value visitIdAtom(IdAtomContext ctx) {
		String id = ctx.getText();
		Value value = memory.get(id);
		if (value == null) {
			throw new RuntimeException("no such variable:" + id);
		}
		return value;
	}

	@Override
	public Value visitStringAtom(StringAtomContext ctx) {
		String str = ctx.getText();
		// strip double quote
		String str1 = str.substring(1, str.length() - 1);
		// unescape ""->"
		// replaceAll?
		return new Value(str1.replace("\"\"", "\""));
	}

	@Override
	public Value visitNilAtom(NilAtomContext ctx) {
		return new Value(null);
	}

}
