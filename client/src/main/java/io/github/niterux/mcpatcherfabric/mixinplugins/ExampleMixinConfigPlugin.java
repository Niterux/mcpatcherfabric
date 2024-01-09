package io.github.niterux.mcpatcherfabric.mixinplugins;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import org.spongepowered.asm.transformers.MixinClassWriter;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//Programmed by LlamaLad7
public class ExampleMixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if (Annotations.getInvisible(targetClass, ReInitializable.class) == null) {
            return;
        }
        for (FieldNode field : targetClass.fields) {
            if ((field.access & Opcodes.ACC_STATIC) == 0) {
                field.access &= ~Opcodes.ACC_FINAL;
            }
        }
		Map<String, MethodNode> ctors = new HashMap<>();
        for (MethodNode method : targetClass.methods) {
            if (method.name.equals("<init>")) {
                ctors.put(method.desc, method);
            }
        }
        for (MethodNode method : targetClass.methods) {
            if (Annotations.getInvisible(method, ReInitializer.class) != null) {
				MethodNode ctor = ctors.get(method.desc);
                if (ctor == null) {
                    throw new IllegalStateException("Could not find matching ctor for " + method);
                }
                copyCtor(targetClass, ctor, method);
            }
        }
    }

	//fixed by wagyourtail
	private void copyCtor(ClassNode owner, MethodNode ctor, MethodNode target) {
		// write ctor to bytes so frame gets computed for us
		ClassWriter cw = new ClassWriter(MixinClassWriter.COMPUTE_FRAMES);
		cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Fake", null, owner.superName, null);
		ctor.accept(cw);
		cw.visitEnd();
		byte[] ctorBytes = cw.toByteArray();
		ClassReader cr = new ClassReader(ctorBytes);
		ClassNode ctorNode = new ClassNode();
		cr.accept(ctorNode, 0);
		// find ctor in new class
		MethodNode ctor2 = null;
		for (MethodNode method : ctorNode.methods) {
			if (method.name.equals("<init>") && method.desc.equals(ctor.desc)) {
				ctor2 = method;
				break;
			}
		}
		if (ctor2 == null) {
			throw new IllegalStateException("Could not find ctor in " + ctorNode);
		}
		// copy instructions from ctor to target
		target.instructions.clear();
		target.instructions.add(ctor2.instructions);

		String name = target.name;
		target.name = "<init>"; // needed for the call below
		Bytecode.DelegateInitialiser initialiser = Bytecode.findDelegateInit(target, owner.superName, owner.name);
		target.name = name;
		if (initialiser == null) {
			throw new IllegalStateException("Could not find initialiser call in ctor " + ctor.desc);
		}
		target.instructions.insert(initialiser.insn, popInitializerArgs(initialiser.insn.desc));
		target.instructions.remove(initialiser.insn);
	}

    private InsnList popInitializerArgs(String desc) {
		InsnList result = new InsnList();
        result.add(new InsnNode(Opcodes.POP)); // The object itself
        for (Type type : Type.getArgumentTypes(desc)) {
            result.insert(new InsnNode(type.getSize() == 2 ? Opcodes.POP2 : Opcodes.POP));
        }
        return result;
    }
}
