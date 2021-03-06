package com.javadocmd.interstellar.model.ai;

import static com.javadocmd.interstellar.model.MyPredicates.TYPE_IS_CITY;

import com.javadocmd.interstellar.model.Map;
import com.javadocmd.interstellar.model.Planet;
import com.javadocmd.interstellar.model.Player;
import com.javadocmd.interstellar.model.Resources;
import com.javadocmd.interstellar.model.ai.AiProgram.Goal;
import com.javadocmd.interstellar.model.work.Jobs;

public class HaveWorkQueues implements Goal {

	final private int count;

	public HaveWorkQueues(int count) {
		this.count = count;
	}

	@Override
	public boolean isAccomplished(Player player, Map map) {
		return player.getWorkQueues().size() >= count;
	}

	@Override
	public void attempt(Player player, Map map) {
		Resources cost = Jobs.costHireAdmin(player);
		Resources deficit = AiUtil.calcCostDeficit(player, map, cost);
		if (deficit == Resources.ZERO) {
			Planet p = AiUtil.pickNonBusyPlanet(player, map, TYPE_IS_CITY);
			if (p != null) {
				player.tryJob(Jobs.hireAdmin(player, p));
			} else {
				// Oh no, it's impossible!
			}
		} else {
			AiUtil.workOnDeficit(player, map, deficit);
		}
	}
}