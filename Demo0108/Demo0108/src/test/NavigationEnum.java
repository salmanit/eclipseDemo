package test;

import android.content.Context;

public enum NavigationEnum {

	SPEED(0, 0, true) {

		@Override
		public String formatInfo(Context context, boolean multiline) {

			return null;
		}
	},
	SUNRISE(0, 0, false) {

		@Override
		public String formatInfo(Context context, boolean multiline) {

			return null;
		}

	};

	private boolean isNavigationOnly;
	private int iconWhite;
	private int iconBlack;

	public abstract String formatInfo(Context context, boolean multiline);

	public int getIconWhite() {
		return iconWhite;
	}

	public void setIconWhite(int iconWhite) {
		this.iconWhite = iconWhite;
	}

	public int getIconBlack() {
		return iconBlack;
	}

	public void setIconBlack(int iconBlack) {
		this.iconBlack = iconBlack;
	}

	public boolean isNavigationOnly() {
		return isNavigationOnly;
	}

	public void setNavigationOnly(boolean isNavigationOnly) {
		this.isNavigationOnly = isNavigationOnly;
	}

	private NavigationEnum(int iconWhite, int iconBlack, boolean isNavigationOnly) {
		this.iconWhite = iconWhite;
		this.iconBlack = iconBlack;
		this.isNavigationOnly = isNavigationOnly;
	}
}
