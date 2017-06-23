package net.algoid.poc;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender.Size;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.matcher.ElementMatchers;

public class bytebuddy {

    enum IntegerSum implements StackManipulation {

        INSTANCE; // singleton

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public Size apply(MethodVisitor methodVisitor,
          Implementation.Context implementationContext) {
            methodVisitor.visitInsn(Opcodes.IADD);
            return new Size(-1, 0);
        }
    }

    enum SumMethod implements ByteCodeAppender {

        INSTANCE; // singleton

        @Override
        public Size apply(MethodVisitor methodVisitor,
          Implementation.Context implementationContext,
          MethodDescription instrumentedMethod) {
            if (!instrumentedMethod.getReturnType().asErasure().represents(int.class)) {
                throw new IllegalArgumentException(instrumentedMethod + " must return int");
            }
            StackManipulation.Size operandStackSize = new StackManipulation.Compound(
              IntegerConstant.forValue(10),
              IntegerConstant.forValue(50),
              IntegerSum.INSTANCE,
              MethodReturn.INTEGER
            ).apply(methodVisitor, implementationContext);
            return new Size(operandStackSize.getMaximalSize(),
              instrumentedMethod.getStackSize());
        }
    }

    enum SumImplementation implements Implementation {

        INSTANCE; // singleton

        @Override
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        @Override
        public ByteCodeAppender appender(Target implementationTarget) {
            return SumMethod.INSTANCE;
        }
    }

    abstract class SumExample {

        public SumExample() {
        }
        
        public abstract int calculate();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        Class<?> dynamicType = new ByteBuddy()
          .subclass(Object.class)
          .method(ElementMatchers.named("toString"))
          .intercept(FixedValue.value("Hello World!"))
          .make()
          .load(bytebuddy.class.getClassLoader())
          .getLoaded();

        System.out.println(dynamicType.newInstance().toString());

        Class<?> dynamicSum = new ByteBuddy()
          .subclass(SumExample.class)
          .method(ElementMatchers.named("calculate"))
          .intercept(SumImplementation.INSTANCE)
          .make()
          .load(bytebuddy.class.getClassLoader())
          .getLoaded();
        
        SumExample sum = (SumExample) dynamicSum.newInstance();
    }
}
