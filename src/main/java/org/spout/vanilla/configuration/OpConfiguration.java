/*
 * This file is part of Vanilla (http://www.spout.org/).
 *
 * Vanilla is licensed under the SpoutDev License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.vanilla.configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.spout.api.exception.ConfigurationException;
import org.spout.api.util.config.ConfigurationHolder;
import org.spout.api.util.config.ConfigurationHolderConfiguration;
import org.spout.api.util.config.yaml.YamlConfiguration;

public class OpConfiguration extends ConfigurationHolderConfiguration {
	public static final ConfigurationHolder OPS = new ConfigurationHolder(Arrays.asList("Notch", "jeb", "ez"), "ops");

	public OpConfiguration(File dataFolder) {
		super(new YamlConfiguration(new File(dataFolder, "ops.yml")));
	}

	@Override
	public void load() throws ConfigurationException {
		super.load();
		super.save();
	}

	public List<String> getOps() {
		return OPS.getStringList();
	}

	public boolean setOp(String playerName, boolean op) {
		List<String> list = getOps();
		if (op) {
			list.add(playerName);
		} else {
			list.remove(playerName);
		}

		OPS.setValue(list);

		try {
			this.save();
			return true;
		} catch (ConfigurationException e) {
			return false;
		}
	}

	public boolean isOp(String playerName) {
		return getOps().contains(playerName);
	}
}
