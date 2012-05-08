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
package org.spout.vanilla.world.generator.flat.biome;

import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.util.cuboid.CuboidShortBuffer;

import org.spout.vanilla.material.VanillaMaterials;
import org.spout.vanilla.world.generator.VanillaBiome;

public class FlatGrassBiome extends VanillaBiome {
	private final int height;

	public FlatGrassBiome(int biomeId) {
		this(biomeId, 64);
	}

	public FlatGrassBiome(int biomeId, final int height) {
		super(biomeId);
		this.height = height;
	}

	@Override
	public void generateColumn(CuboidShortBuffer blockData, int x, int chunkY, int z) {
		final int startY = chunkY * Chunk.CHUNK_SIZE;
		final int endY = Math.min(Chunk.CHUNK_SIZE + startY, height);
		for (int y = startY; y < endY; y++) {
			if (y <= 0) {
				blockData.set(x, y, z, VanillaMaterials.BEDROCK.getId());
			} else if (y == height - 1) {
				blockData.set(x, y, z, VanillaMaterials.GRASS.getId());
			} else {
				blockData.set(x, y, z, VanillaMaterials.DIRT.getId());
			}
		}
	}

	@Override
	public String getName() {
		return "Flat";
	}
}