/*
 *  This file is part of SiVi.
 *
 *  SiVi is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published
 *  by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  SiVi is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with SiVi.  If not, see
 *  <http://www.gnu.org/licenses/>.
 */
package com.adonax.texturebuilder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Immutable class that contains the parameters for combining the textures
 * into a single texture.
 *
 * There are 4 channels and each one has a channel mode and slider value.
 *
 * Channels are then grouped by their channel mode.  Each group then has its own
 * group mode and slider value.
 */
public class CombineParams {

	public enum ChannelMode {
		ADD,
		LERP,
		SIN,
		XDIM,
		YDIM,
		CIRC
	}

	public enum GroupMode {
		ADD,
		LERP,
		RING
	}

	private final int CHANNELS = 4;

	private final ChannelMode[] channelModes = new ChannelMode[CHANNELS];
	private final int[] stage1weights = new int[CHANNELS];  // weight stage 1

	private final Map<ChannelMode, GroupMode> groupModes;
	private final Map<ChannelMode, Integer> stage2weights;  // weight stage 2

	public CombineParams( ChannelMode[] channelModes,
						          int[] stage1weights,
			Map<ChannelMode, GroupMode> groupModes,
			Map<ChannelMode, Integer>   stage2weights) {

		assert(channelModes.length == CHANNELS);
		assert(stage1weights.length == CHANNELS);

		Set<ChannelMode> specifiedModes = new HashSet<ChannelMode>();

		for (int i = 0;  i < CHANNELS;  i++) {
			this.channelModes[i] = channelModes[i];
			this.stage1weights[i] = stage1weights[i];
			specifiedModes.add(channelModes[i]);
		}

		assert(specifiedModes.equals(groupModes.keySet()));
		assert(specifiedModes.equals(stage2weights.keySet()));

		this.groupModes = groupModes;
		this.stage2weights = stage2weights;
	}

	public ChannelMode getChannelMode(int index) {
		return channelModes[index];
	}

	public int getStage1Weight(int index) {
		return stage1weights[index];
	}

	public Set<ChannelMode> getGroups() {
		return groupModes.keySet();
	}

	public GroupMode getGroupMode(ChannelMode mode) {
		return groupModes.get(mode);
	}

	public int getStage2Weight(ChannelMode mode) {
		return stage2weights.get(mode);
	}
}
