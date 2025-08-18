package com.craftinginterpreters.lox;

import java.util.Scanner;

public class RpnPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr){
        StringBuilder builder = new StringBuilder();

        builder.append(expr.left.accept(this) + " ");
        builder.append(expr.right.accept(this) + " ");
        builder.append(expr.operator.lexeme);

        return builder.toString();
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr){
        return expr.expression.accept(this);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr){
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr){
        StringBuilder builder = new StringBuilder();

        builder.append(expr.operator.lexeme + " ");
        builder.append(expr.right.accept(this) + " ");

        return builder.toString();
    }


    public static void main(String[] args){
        Expr expr = new Expr.Binary(
            new Expr.Binary(
                new Expr.Literal(3),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Literal(3)
            ),
            new Token(TokenType.AND, "+", null, 1),
            new Expr.Binary(
                new Expr.Literal(8),
                new Token(TokenType.SLASH, "/", null, 1),
                new Expr.Literal(0)
            )
        );

        RpnPrinter printer = new RpnPrinter();
        System.out.println(printer.print(expr));
    }
}
