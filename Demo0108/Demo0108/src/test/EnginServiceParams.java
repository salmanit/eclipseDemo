package test;

public class EnginServiceParams {
	
	
	private static EnginServiceParams smInstance;

	/**
	 * @return The instance parameters. Can return null if not yet created. However,
	 *         creation should happen in the Application's onCreate.
	 */
	public static EnginServiceParams getInstance() {
		return smInstance;
	}

	public static void registerInstance(final EnginServiceParams params) {
		if (null == smInstance) {
			smInstance = params;
		} else {
			throw new RuntimeException("You cannot initialize the EngineServiceParemeters twice");
		}
	}
}
