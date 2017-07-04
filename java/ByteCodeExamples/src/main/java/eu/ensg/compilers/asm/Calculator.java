/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ensg.compilers.asm;

import eu.ensg.compilers.ast.Addition;
import eu.ensg.compilers.ast.Ast;
import eu.ensg.compilers.ast.Division;
import eu.ensg.compilers.ast.Minus;
import eu.ensg.compilers.ast.Multiplication;
import eu.ensg.compilers.ast.Number;
import eu.ensg.compilers.ast.Print;
import eu.ensg.compilers.ast.Substration;
import eu.ensg.compilers.ast.Visitor;
import eu.ensg.compilers.utils.ByteClassLoader;
import eu.ensg.compilers.utils.RessourcesCounter;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;
import org.objectweb.asm.Type;

/**
 *
 * @author cyann
 */
public class Calculator {

    private static class ASMVisitor implements Visitor {

        private final MethodVisitor mv;
        private final RessourcesCounter stack;
        private final RessourcesCounter local;

        public int getMaxStack() {
            return stack.getMax();
        }

        public int getMaxLocalVariable() {
            return local.getMax();
        }

        public ASMVisitor(MethodVisitor mv, int initialStack, int initialLocal) {
            this.mv = mv;
            this.stack = new RessourcesCounter(initialStack);
            this.local = new RessourcesCounter(initialLocal);
        }

        @Override
        public void visite(Addition ast) {
            ast.getRight().visite();
            ast.getLeft().visite();

            mv.visitVarInsn(ILOAD, local.decrement().getValue()); // récupère left
            mv.visitVarInsn(ILOAD, local.decrement().getValue()); // récupère right
            mv.visitInsn(IADD); // addition
            mv.visitVarInsn(ISTORE, local.getValue());
            local.increment();
        }

        @Override
        public void visite(Substration ast) {
            ast.getRight().visite();
            ast.getLeft().visite();

            mv.visitVarInsn(ILOAD, local.decrement().getValue()); // récupère left
            mv.visitVarInsn(ILOAD, local.decrement().getValue()); // récupère right
            mv.visitInsn(ISUB); // soustraction
            mv.visitVarInsn(ISTORE, local.getValue());
            local.increment();
        }

        @Override
        public void visite(Multiplication ast) {
            ast.getRight().visite();
            ast.getLeft().visite();

            mv.visitVarInsn(ILOAD, local.decrement().getValue()); // récupère left
            mv.visitVarInsn(ILOAD, local.decrement().getValue()); // récupère right
            mv.visitInsn(IMUL); // multiplication
            mv.visitVarInsn(ISTORE, local.getValue());
            local.increment();
        }

        @Override
        public void visite(Division ast) {
            ast.getRight().visite();
            ast.getLeft().visite();

            mv.visitVarInsn(ILOAD, local.decrement().getValue()); // récupère left
            mv.visitVarInsn(ILOAD, local.decrement().getValue()); // récupère right
            mv.visitInsn(IDIV); // division
            mv.visitVarInsn(ISTORE, local.getValue());
            local.increment();
        }

        @Override
        public void visite(Minus ast) {
            ast.getLeft().visite();

            mv.visitVarInsn(ILOAD, local.decrement().getValue()); // récupère left
            mv.visitInsn(INEG); // négation
            mv.visitVarInsn(ISTORE, local.getValue());
            local.increment();
        }

        @Override
        public void visite(Number ast) {
            stack.increment();
            mv.visitIntInsn(BIPUSH, ast.getValue());

            stack.decrement();
            mv.visitVarInsn(ISTORE, local.getValue());
            local.increment();
        }

        @Override
        public void visite(Print ast) {
            stack.increment();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            ast.getExpression().visite();
            mv.visitVarInsn(ILOAD, local.decrement().getValue());
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            stack.decrement();
        }
    }

    public static void main(String[] args) {

        // build ast
        Ast ast = new Print(new Addition(new Minus(new Number(2)), new Substration(new Number(7), new Number(3))));
        ASMVisitor astVisitor;

        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(V1_8, ACC_PUBLIC, "asm/Calculator", null, "java/lang/Object", null);
        {
            // constructor
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitMaxs(2, 1);
            mv.visitVarInsn(ALOAD, 0); // push `this` to the operand stack
            mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(Object.class), "<init>", "()V", false); // call the constructor of super class
            mv.visitInsn(RETURN);
            mv.visitEnd();

        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);

            // compile through ast
            astVisitor = new ASMVisitor(mv, 1, 1);
            ast.accept(astVisitor);
            ast.visite();

            mv.visitInsn(RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("args", "[Ljava/lang/String;", null, l0, l1, 0);
            mv.visitMaxs(astVisitor.getMaxStack(), astVisitor.getMaxLocalVariable() + 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        System.out.println("max stack: " + astVisitor.getMaxStack());
        System.out.println("max local variable: " + astVisitor.getMaxLocalVariable());

        ByteClassLoader.ExecuteByteCode("asm.Calculator", cw.toByteArray());
    }

}
