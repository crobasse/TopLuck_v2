package fr.cleyhanrb.topluck;

import fr.cleyhanrb.topluck.enums.TopLuckItem;

public class TopLuckPlayer {

	public String Pseudo;
	private int MINED_BLOCKS = 0;
	private int MINED_COBBLE = 0;
	private int MINED_DIAMOND = 0;
	private int MINED_EMERALD = 0;
	private int MINED_GOLD = 0;
	private int MINED_IRON = 0;
	private int MINED_COAL = 0;
	private int MINED_REDSTONE = 0;
	private int MINED_LAPIS = 0;
	private int MINED_NETHER = 0;
	
	public TopLuckPlayer(String Pseudo) {
		this.Pseudo = Pseudo;
	}
	
	public void setData(TopLuckItem item, int count) {
		switch(item) {
		case BLOCKS:
			MINED_BLOCKS = count;
			break;
		case COAL:
			MINED_COAL = count;
			break;
		case COBBLE:
			MINED_COBBLE = count;
			break;
		case DIAMOND:
			MINED_DIAMOND = count;
			break;
		case EMERALD:
			MINED_EMERALD = count;
			break;
		case GOLD:
			MINED_GOLD = count;
			break;
		case IRON:
			MINED_IRON = count;
			break;
		case LAPIS:
			MINED_LAPIS = count;
			break;
		case NETHER:
			MINED_NETHER = count;
			break;
		case REDSTONE:
			MINED_REDSTONE = count;
			break;
		default:
			break;
		
		}
	}
	
	public int getData(TopLuckItem item) {
		switch(item) {
		case BLOCKS:
			return MINED_BLOCKS;
		case COAL:
			return MINED_COAL;
		case COBBLE:
			return MINED_COBBLE;
		case DIAMOND:
			return MINED_DIAMOND;
		case EMERALD:
			return MINED_EMERALD;
		case GOLD:
			return MINED_GOLD;
		case IRON:
			return MINED_IRON;
		case LAPIS:
			return MINED_LAPIS;
		case NETHER:
			return MINED_NETHER;
		case REDSTONE:
			return MINED_REDSTONE;
		default:
			return 0;
		
		}
	}
	
}
