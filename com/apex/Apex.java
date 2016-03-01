package com.apex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Apex {

	private int cycle;
	private int no_of_cycles = 0;
	private int R[], X, MEM[];
	private int pc, ins_size, d;
	private int temp;
	private List<String> instructions;
	private String ins;
	private List<Gantt> gantt = new ArrayList<Gantt>();
	private List<Instruction> cache;
	private List<Integer> dep;
	private List<Result> result;
	private Scanner scan;
	private int lock = 0;
	private int res;;
	private boolean all, branch;

	Apex() {
		prompt();
	}
	int nxt;
	
	private class Result {
		int des;
		int result;

		Result() {
			// TODO Auto-generated constructor stub
			des = -1;
			result = -1;

		}
	}

	public void prompt() {
		int choice;
		System.out
				.println("[1]Initilize\n[2]Simulate <n>\n[3]Display\n[4]Exit");
		System.out.println("Enter Your Choice :\n");
		Scanner scan = new Scanner(System.in);
		choice = scan.nextInt();
		switch (choice) {
		case 1:
			initilize();
			no_of_cycles = 0;
			System.out.println("Initilization Complete....");
			prompt();
			break;
		case 2:
			simulate_n();
			break;
		case 3:
			display();
			break;
		case 4:
			System.out.println("Simulater Terminated..");
			scan.close();
			return;
		default:
			System.out.println("Simulater Terminated...");
			return;

		}
	}

	public void initilize() {
		pc = 0;
		cycle = 0;
		R = new int[9];
		MEM = new int[10000];
		all = false;
		branch = false;
		cache = new ArrayList<Instruction>();
		dep = new ArrayList<Integer>();
		result = new ArrayList<Apex.Result>();
		for (int i = 0; i < 9; i++)
			R[i] = 0;
		for (int i = 0; i < 1000; i++)
			MEM[i] = 0;
		instructions = new ArrayList<String>();
		instructions.add("");
		try (BufferedReader br = new BufferedReader(new FileReader(
				"/home/vaibhav/Disk/SoftwareDevelopment/Eclipse/EclipseProjects/Apex/com/apex/Input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				// process the line.
				instructions.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ins_size = instructions.size();
		cache.add(null);
		dep.add(-1);
		Instruction c = new Instruction();
		c.decode(instructions.get(1));
		cache.add(c);
		dep.add(-1);
		for (int i = 2; i < ins_size; i++) {
			Instruction c1 = new Instruction();
			c1.decode(instructions.get(i));
			cache.add(c1);
			d = -1;
			for (int x = i - 1; x >= 1; x--) {
				if (c1.src1 == cache.get(x).des || c1.src2 == cache.get(x).des || c1.src3 == cache.get(x).des) {
					d = x;
					break;
				}
			}
			dep.add(d);
		}
		cache.clear();
		/*
		 * System.out.println("Dependency.."); for(int i=1;i<ins_size;i++){
		 * System.out.println("I"+i+" : I"+dep.get(i)); }
		 */
	}

	public void display() {
		System.out.println("Gantt Chart...");
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= no_of_cycles; j++) {
				if (gantt.get(j).stage[i] == 0) {
					System.out.print(" X  ");
				} else {
					System.out.printf("%4s", "I" + gantt.get(j).stage[i] + " ");
				}
			}
			System.out.println();
		}
		/*
		 * System.out.println("Contents in Stages"); int c=no_of_cycles;
		 * if(gantt.get(c).stage[1]==0){ while(gantt.get(c).stage[1]==0) c--;
		 * System.out.println("Fetch : I"+(gantt.get(c).stage[1]+1)); }else{
		 * System.out.println("Fetch : I"+gantt.get(c).stage[1]); }
		 * c=no_of_cycles; if(gantt.get(c).stage[2]==0){
		 * while(gantt.get(c).stage[2]==0) c--; }
		 * System.out.println("Decode : I"+gantt.get(c).stage[2]);
		 * System.out.println("Execute : I"+gantt.get(no_of_cycles).stage[3]);
		 * System.out.println("Mem : I"+gantt.get(no_of_cycles).stage[4]);
		 * System
		 * .out.println("Write Back : I"+gantt.get(no_of_cycles).stage[5]);
		 * System.out.println("fetch : "+f+ "\ndecode : "+dr+ "\nexecute : "+e+
		 * "\nmem : "+m+ "\nwrite : "+w);
		 */
		int w,m,e,d,f;
		w=gantt.get(no_of_cycles).stage[5];
		m=gantt.get(no_of_cycles).stage[4];
		e=gantt.get(no_of_cycles).stage[3];
		d=gantt.get(no_of_cycles).stage[2];
		f=gantt.get(no_of_cycles).stage[1];
		System.out.println("\nContents of each stage :");
		if(f==0)
			System.out.println("Fetch Stage : X");
		else
			System.out.println("Fetch Stage : I"+f);
		if(d==0)
			System.out.println("Decode R/F Stage : X");
		else
			System.out.println("Decode R/F Stage : I"+d);
		if(e==0)
			System.out.println("Execution Stage : X");
		else
			System.out.println("Execution Stage : I"+e);
		if(m==0)
			System.out.println("Memory Stage : X");
		else
			System.out.println("Memory Stage : I"+m);
		if(w==0)
			System.out.println("Write Back Stage : X");
		else
			System.out.println("Write Back Stage : I"+w);
		
		
		
		System.out.println("\nRegister values :");
		for (int i = 0; i < 8; i++) {
			System.out.println("R" + i + " : " + R[i]);
		}
		System.out.println("X : " + R[8]);
		System.out.println("MEM...");
		for (int i = 0; i < 1000; i++) {
			if (MEM[i] != 0)
				System.out.println("MEM[" + i + "] : " + MEM[i]);
		}
		;
		prompt();
	}

	public void absolute_branch() {
		int temp_no_of_cycles = no_of_cycles;
		int addr = temp;
		gantt.get(cycle+2).stage[3] = pc;
		nxt = pc+20000;
		no_of_cycles = cycle + 2;
		fetch();
		lock = 0;
		pc = addr;
		cycle = no_of_cycles;
		no_of_cycles = temp_no_of_cycles;
		branch = true;
	}

	public void relative_branch() {
		int temp_no_of_cycles = no_of_cycles;
		int temp_pc = pc;
		int r_addr = temp;
		gantt.get(cycle+2).stage[3] = pc;
		no_of_cycles = cycle + 2;
		fetch();
		lock = 0;
		pc = temp_pc + r_addr;
		cycle = no_of_cycles;
		no_of_cycles = temp_no_of_cycles;
		branch = true;
	}

	public void simulate_n() {
		initilize();
		scan = new Scanner(System.in);
		System.out.print("Enter No of Cycles to be executed : ");
		no_of_cycles = no_of_cycles + scan.nextInt();
		result.clear();
		for (int i = 0; i <= no_of_cycles; i++) {
			result.add(new Result());
		}
		gantt.clear();
		for (int i = 0; i <= no_of_cycles + 1; i++) {
			Gantt g = new Gantt();
			gantt.add(g);
		}
		System.out.println("\nSimulation begins...\n");
		// try{
		fetch();
		// }catch(Exception e){
		// System.out.println("Problem at Cycle : "+(cycle+3)+" Instruction : I"+pc);
		// return;
		// }
		System.out.println("Simulation Ends....");
		System.out.println("\n\n");
		prompt();
	}

	private void isAllExecuted() {
		if (all) {
			no_of_cycles = cycle + 4;
			System.out.println("All instructions executed in " + (cycle + 4)
					+ " cycles\n");
		}
	}

	private void fetch() {

		cycle++;
		if (cycle <= no_of_cycles) {
			pc++;
			result.add(new Result());
			if (pc < ins_size - 1) {
				all = false;
				ins = instructions.get(pc);
				gantt.get(cycle).stage[1] = pc;
				decode(ins);
			} else if (pc == ins_size - 1) {
				ins = instructions.get(pc);
				gantt.get(cycle).stage[1] = pc;
				all = true;
				decode(ins);

			} else {
			}
		} else {
		}
	}

	private void decode(String ins) {
		if (cycle + 1 <= no_of_cycles) {
			if (pc + 1 < ins_size) {
				gantt.get(cycle + 1).stage[1] = pc + 1;
			}
			
			Instruction in = new Instruction();
			in.decode(ins);
			if(!branch){
			d = dep.get(pc);
			if (d != -1) {// dep
				lock = 1;
				gantt.get(cycle + 1).stage[2] = pc;
				for (int i = 1; i <= no_of_cycles; i++) {
					temp = -1;
					if (gantt.get(i).stage[5] == d) {
						temp = i;
						break;
					}
				}
				if (temp != -1) {
					if (temp >= cycle + 1) {
						cycle = temp;
					} else {
					}
					if (cycle + 1 <= no_of_cycles) {
						gantt.get(cycle + 1).stage[2] = pc;
						execute(in);
					} else {
					}
				} else {
				}
			} else {// without dep
				gantt.get(cycle + 1).stage[2] = pc;
				execute(in);
			}
			}else{gantt.get(cycle + 1).stage[2] = pc;
			execute(in);}
		} else {
			fetch();
		}
	}

	private int value(int r) {
		int value = -1;
		for (int i = cycle + 1; i >= 1; i--) {
			if (r == result.get(i).des) {
				value = result.get(i).result;
				break;
			}
		}
		return value;
	}

	private void execute(Instruction in) {
		if (cycle + 2 <= no_of_cycles) {
			switch (in.type) {
			case 0:
				temp = in.lit;
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;
			case 1:
				if (branch) {
					temp = value(in.src1) + value(in.src2);
				} else {
					temp = R[in.src1] + R[in.src2];
				}
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;
			case 2:
				if (branch) {
					temp = value(in.src1) - value(in.src2);
				} else {
					temp = R[in.src1] - R[in.src2];
				}
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;
			case 3:
				if (branch) {
					temp = value(in.src1) * value(in.src2);
				} else {
					temp = R[in.src1] * R[in.src2];
				}
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;
			case 4:
				if (branch) {
					temp = value(in.src1) & value(in.src2);
				} else {
					temp = R[in.src1] & R[in.src2];
				}
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;
			case 5:
				if (branch) {
					temp = value(in.src1) | value(in.src2);
				} else {
					temp = R[in.src1] | R[in.src2];
				}
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;
			case 6:
				if (branch) {
					temp = value(in.src1) ^ value(in.src2);
				} else {
					temp = R[in.src1] ^ R[in.src2];
				}
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;
			case 7:
				if (in.lit != -1) {
					if(branch){
						temp = MEM[value(in.src1) + in.lit];
					}else{
					temp = MEM[R[in.src1] + in.lit];
					}
				} else {
					if(branch){
						temp = MEM[value(in.src1) + value(in.src2)];
					}else{
					temp = MEM[R[in.src1] + R[in.src2]];
					}
				}
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;
			case 8:
				if(branch){
					temp = value(in.src1);
				}else{
				temp = R[in.src1];
				}
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;
			case 9:all = false;
			    if(in.src1==8){
			    	temp = R[8] - in.lit -20000;
			    }else{
				if(branch){
					temp = value(in.src1) + in.lit - 20000;
				}else{
				temp = R[in.src1] + in.lit - 20000;
				}
			    }
			    absolute_branch();
				if(lock==0){
					fetch();}
				break;
			case 10:
				temp = -1;
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;
			case 11:
				temp = in.lit;
				if (res == 0) {
					relative_branch();
					if(lock==0){
						fetch();
					}
				} else {
					gantt.get(cycle + 2).stage[3] = pc;
					mem(in, temp);
				}
				break;
			case 12:
				temp = in.lit;
				if (res != 0) {
					relative_branch();
					if(lock==0){
						fetch();
					}
				} else {
					gantt.get(cycle + 2).stage[3] = pc;
					mem(in,temp);
				}
				break;
			case 13:
				all = false;
				if(branch){
					temp = value(in.src1) + in.lit - 20000;
				}else{
				temp = R[in.src1] + in.lit - 20000;
				}
				absolute_branch();
				R[8] = nxt;
				if(lock==0){
					fetch();
				}
				break;
			case 14:
				if(branch){
					temp = value(in.src1);
				}else{
				temp = R[in.src1];
				}
				gantt.get(cycle + 2).stage[3] = pc;
				mem(in, temp);
				break;

			}
		} else {
			fetch();
		}
	}

	private void mem(Instruction in, int temp) {
		if (cycle + 3 <= no_of_cycles) {
			if (in.type == 8) {
				if(in.lit!=-1){
				if (R[in.src2] + in.lit >= 0 && R[in.src2] + in.lit <= 9999)
					MEM[R[in.src2] + in.lit] = temp;
				}else{
					if (R[in.src2] + in.lit >= 0 && R[in.src2] + in.lit <= 9999)
						MEM[R[in.src2] + R[in.src3]] = temp;
				}
			}
			gantt.get(cycle + 3).stage[4] = pc;
			write(in, temp);
		} else {
			fetch();
		}
	}

	private void write(Instruction in, int temp) {
		if (cycle + 4 <= no_of_cycles) {
			lock = 0;
			if (in.type == 8||in.type==11||in.type==12) {
				gantt.get(cycle + 4).stage[5] = pc;
				isAllExecuted();
			} else if (in.type == 10) {
				gantt.get(cycle + 4).stage[5] = pc;
				no_of_cycles = cycle + 4;
				System.out.println("HALT instruction encountered at cycle "
						+ cycle);
			} else {
				R[in.des] = temp;
				res = R[in.des];
				result.get(cycle + 4).des = in.des;
				result.get(cycle + 4).result = res;
				gantt.get(cycle + 4).stage[5] = pc;
				isAllExecuted();
			}

		} else {
			fetch();
		}

		if (lock == 0) {
			fetch();
		}
	}

}
