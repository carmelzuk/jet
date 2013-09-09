package jet.com.impl.modules.test.hooks;

import jet.com.modules.command.abs.AbsOptstringHook;

public class OptstringHook extends AbsOptstringHook {

	@Override
	public String[] optstrings() {
		String[] opts = new String[]{
				"t"
		};
		return opts;
	}

}
