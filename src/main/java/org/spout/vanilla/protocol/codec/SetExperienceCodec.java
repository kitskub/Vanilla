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
package org.spout.vanilla.protocol.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.vanilla.protocol.msg.SetExperienceMessage;

import java.io.IOException;

public class SetExperienceCodec extends MessageCodec<SetExperienceMessage> {
	public SetExperienceCodec() {
		super(SetExperienceMessage.class, 0x2B);
	}

	@Override
	public SetExperienceMessage decode(ChannelBuffer buffer) throws IOException {
		float barValue = buffer.readFloat();
		short level = buffer.readShort();
		short totalExp = buffer.readShort();
		return new SetExperienceMessage(barValue, level, totalExp);
	}

	@Override
	public ChannelBuffer encode(SetExperienceMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(8);
		buffer.writeFloat(message.getBarValue());
		buffer.writeShort(message.getLevel());
		buffer.writeShort(message.getTotalExp());
		return buffer;
	}
}