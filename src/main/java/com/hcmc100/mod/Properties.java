package com.hcmc100.mod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Properties {

	private File file;

	private CompoundTag properties = new CompoundTag();

	public Properties(File file) {
		this.file = file;
		read();
	}

	public void read(){
		if (file.exists()) {
			try (FileInputStream stream = new FileInputStream(file)) {
				properties = NbtIo.readCompressed(stream);
			} catch (Exception ex) {
				throw new RuntimeException("failed to read properties", ex);
			}
		}
	}

	public void save() {
		try (FileOutputStream stream = new FileOutputStream(file)) {
			NbtIo.writeCompressed(properties, stream);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public CompoundTag get() {
		return properties;
	}

	public String getID() {
		return get().getString("id");
	}
}
