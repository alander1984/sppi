package ru.sbrf.bh.asset.sppi.branch.spring;

import ru.sbrf.ufs.platform.core.spring.context.PlatformNamespaceHandler;

public class NamespaceHandler extends PlatformNamespaceHandler {
	@Override
	protected String getTemplatesPath() {
		return "/ru/sbrf/bh/asset/sppi/branch/ftl/";
	}

	@Override
	public void init() {
		// TODO: remove it
		String templatesPath = getTemplatesPath();
	}
}
