/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011 Spout LLC <http://www.spout.org/>
 * Vanilla is licensed under the Spout License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.vanilla.init;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Test;

import org.spout.vanilla.EngineFaker;
import org.spout.vanilla.TestVanilla;
import org.spout.vanilla.data.configuration.VanillaConfiguration;
import org.spout.vanilla.material.VanillaMaterial;
import org.spout.vanilla.material.VanillaMaterials;
import org.spout.vanilla.material.block.solid.Stone;

import static org.junit.Assert.fail;

public class StaticInitialisationOrderTest {
	@Test
	public void materialStaticInitialisationTest() {
		TestVanilla.init();
		VanillaConfiguration config = TestVanilla.getInstance().getConfig();
		VanillaConfiguration.LAVA_DELAY.setConfiguration(config);
		VanillaConfiguration.WATER_DELAY.setConfiguration(config);

		EngineFaker.setupEngine();
		try {
			new Stone("Test Stone", 87945);
			VanillaMaterials.initialize();
			for (Field field : VanillaMaterials.class.getFields()) {
				try {
					if (field == null || ((field.getModifiers() & (Modifier.STATIC | Modifier.PUBLIC)) != (Modifier.STATIC | Modifier.PUBLIC)) || !VanillaMaterial.class.isAssignableFrom(field.getType())) {
						continue;
					}
					VanillaMaterial material = (VanillaMaterial) field.get(null);
					if (material == null) {
						fail("invalid material: VanillaMaterials field '" + field.getName() + "' is null");
					}
				} catch (NoClassDefFoundError ex) {
					staticInitFail(ex);
				} catch (Throwable t) {
					t.printStackTrace();
					fail("invalid material: An exception occurred while loading/reading VanillaMaterials field '" + field.getName() + "'");
				}
			}
		} catch (NoClassDefFoundError t) {
			staticInitFail(t);
		} catch (ExceptionInInitializerError e) {
			// This catches initialization failures properly.  but will cause all subsequent tests to Error.
			staticInitFail(e.getCause());
		}
	}

	public static void staticInitFail(Throwable t) {
		String s = "";
		while (t != null) {
			s += t.getMessage() + "\n";
			t.printStackTrace();
			t = t.getCause();
		}
		fail(s + ": Static initialisation of VanillaMaterials failed.");
	}
}
