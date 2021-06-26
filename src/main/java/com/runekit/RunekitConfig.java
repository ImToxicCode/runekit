package com.runekit;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("runekit")
public interface RunekitConfig extends Config
{
	String CONFIG_GROUP = "runekit";

	@ConfigItem(
			keyName = "email",
			name = "Email",
			description = "RuneKit user email address",
			position = 0
	)
	String username();

	@ConfigItem(
			keyName = "token",
			name = "Token",
			description = "RuneKit user token.",
			secret = true,
			position = 1
	)
	String token();
}
