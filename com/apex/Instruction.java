package com.apex;

public class Instruction {

	int type;
	int des;
	int src1;
	int src2;
	int src3;
	int lit;

	public Instruction() {
		// TODO Auto-generated constructor stub
		type = -10;
		des = -1;
		src1 = -1;
		src2 = -1;
		src3 = -1;
		lit = -1;
	}

	public void decode(String ins) {
		if (ins.startsWith("MOVC")) {
			type = 0;
			des = Integer.parseInt("" + ins.charAt(6));
			lit = Integer.parseInt(ins.substring(8) + "");
			;
		} else if (ins.startsWith("ADD")) {
			type = 1;
			des = Integer.parseInt("" + ins.charAt(5));
			src1 = Integer.parseInt("" + ins.charAt(8));
			src2 = Integer.parseInt("" + ins.charAt(11));
		} else if (ins.startsWith("SUB")) {
			type = 2;
			des = Integer.parseInt("" + ins.charAt(5));
			src1 = Integer.parseInt("" + ins.charAt(8));
			src2 = Integer.parseInt("" + ins.charAt(11));
		} else if (ins.startsWith("MUL")) {
			type = 3;
			des = Integer.parseInt("" + ins.charAt(5));
			src1 = Integer.parseInt("" + ins.charAt(8));
			src2 = Integer.parseInt("" + ins.charAt(11));
		} else if (ins.startsWith("AND")) {
			type = 4;
			des = Integer.parseInt("" + ins.charAt(5));
			src1 = Integer.parseInt("" + ins.charAt(8));
			src2 = Integer.parseInt("" + ins.charAt(11));
		} else if (ins.startsWith("OR")) {
			type = 5;
			des = Integer.parseInt("" + ins.charAt(4));
			src1 = Integer.parseInt("" + ins.charAt(7));
			src2 = Integer.parseInt("" + ins.charAt(10));
		} else if (ins.startsWith("EX-OR")) {
			type = 6;
			des = Integer.parseInt("" + ins.charAt(7));
			src1 = Integer.parseInt("" + ins.charAt(10));
			src2 = Integer.parseInt("" + ins.charAt(13));
		} else if (ins.startsWith("LOAD")) {
			type = 7;
			des = Integer.parseInt("" + ins.charAt(6));
			src1 = Integer.parseInt("" + ins.charAt(9));
			if (ins.charAt(11) == 'R') {
				src2 = Integer.parseInt("" + ins.charAt(12));
			} else {
				lit = Integer.parseInt("" + ins.substring(11));
			}

		} else if (ins.startsWith("STORE")) {
			type = 8;
			des = -8;
			src1 = Integer.parseInt("" + ins.charAt(7));
			src2 = Integer.parseInt("" + ins.charAt(10));
			if (ins.charAt(12) == 'R') {
				src3 = Integer.parseInt("" + ins.charAt(13));
			} else {
				lit = Integer.parseInt("" + ins.substring(12));
			}
		}
		else if (ins.startsWith("JUMP")) {
			type = 9;
			des = -9;
			if (ins.charAt(5) == 'X') {
				src1 = 8;
				lit = Integer.parseInt("" + ins.substring(7));
			} else {
				src1 = Integer.parseInt("" + ins.charAt(6));
				lit = Integer.parseInt("" + ins.substring(8));
			}
		} else if (ins.startsWith("HALT")) {
			type = 10;
			des = -10;
		} else if (ins.startsWith("BZ")) {
			type = 11;
			des = -11;
			lit = Integer.parseInt("" + ins.substring(3)) - 1;
		} else if (ins.startsWith("BNZ")) {
			type = 12;
			des = -12;
			lit = Integer.parseInt("" + ins.substring(4)) - 1;
		} else if (ins.startsWith("BAL")) {
			type = 13;
			des = -13;
			src1 = Integer.parseInt("" + ins.charAt(5));
			lit = Integer.parseInt("" + ins.substring(7));
		} else if (ins.startsWith("MOV")) {
			type = 14;
			if (ins.charAt(4) == 'X') {
				des = 8;
				if (ins.charAt(6) == 'X') {
					src1 = 8;
				} else {
					src1 = Integer.parseInt("" + ins.charAt(7));
				}
			} else {
				des = Integer.parseInt("" + ins.charAt(5));
				if (ins.charAt(7) == 'X') {
					src1 = 8;
				} else {
					src1 = Integer.parseInt("" + ins.charAt(8));
				}
			}
		} else {
			type = -1;
		}
	}

	public void show() {
		System.out.println("Type : " + type + "\nDes : " + des + "\nsrc1 : "
				+ src1 + "\nsrc2 : " + src2 + "\nsrc3 : " + src3 + "\nlit : "
				+ lit);
	}

}
