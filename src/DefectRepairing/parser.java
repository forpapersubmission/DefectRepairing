package DefectRepairing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class parser {

	private static class Variable implements Cloneable, Comparable<Variable> {
		String Name;
		String Type;
		Object Value;
		Boolean Defined;
		LineNumber scopeendLine;
		LineNumber scopestartLine;

		Variable() {
		}

		Variable(String _Name, String _Type, Object _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		public Object clone() {
			Variable o = null;
			try {
				o = (Variable) super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return o;
		}

		@Override
		public String toString() {
			return "Variable [Name=" + Name + ", Type=" + Type + ", Value=" + Value + ", Defined=" + Defined + "]";
		}

		@Override
		public int compareTo(Variable arg0) {
			// TODO Auto-generated method stub
			return Name.compareTo(((Variable) arg0).Name);
		}
	}

	private static class VarInt extends Variable {
		int Value;

		VarInt(String _Name, String _Type, int _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		@Override
		public String toString() {
			return "VarInt [Name=" + Name + ", Value=" + Value + "]";
		}
	}

	private static class VarLong extends Variable {
		long Value;

		VarLong(String _Name, String _Type, long _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		@Override
		public String toString() {
			return "VarLong [Name=" + Name + ", Value=" + Value + "]";
		}
	}

	private static class VarByte extends Variable {
		byte Value;

		VarByte(String _Name, String _Type, Byte _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		@Override
		public String toString() {
			return "VarByte [Name=" + Name + ", Value=" + Value + "]";
		}
	}

	private static class VarString extends Variable {
		String Value;

		VarString(String _Name, String _Type, String _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		@Override
		public String toString() {
			return "VarString [Name=" + Name + ", Value=" + Value + "]";
		}
	}

	private static class VarShort extends Variable {
		short Value;

		VarShort(String _Name, String _Type, short _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		@Override
		public String toString() {
			return "VarShort [Name=" + Name + ", Value=" + Value + "]";
		}
	}

	private static class VarChar extends Variable {
		char Value;

		VarChar(String _Name, String _Type, char _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		@Override
		public String toString() {
			return "VarChar [Name=" + Name + ", Value=" + Value + "]";
		}
	}

	private static class VarFloat extends Variable {
		float Value;

		VarFloat(String _Name, String _Type, float _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		@Override
		public String toString() {
			return "VarFloat [Name=" + Name + ", Value=" + Value + "]";
		}
	}

	private static class VarDouble extends Variable {
		double Value;

		VarDouble(String _Name, String _Type, double _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		@Override
		public String toString() {
			return "VarDouble [Name=" + Name + ", Value=" + Value + "]";
		}
	}

	private static class VarBoolean extends Variable {
		boolean Value;

		VarBoolean(String _Name, String _Type, boolean _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		@Override
		public String toString() {
			return "VarBoolean [Name=" + Name + ", Value=" + Value + "]";
		}
	}

	private static class VarObject extends Variable {
		VarObject(String _Name, String _Type, Object _Value) {
			Name = _Name;
			Type = _Type;
			Value = _Value;
		}

		public String toString() {
			return "VarObect [Name=" + Name + "]";
		}
	}

	private static class Statement {
		String StmtType;
		String Filename;

		void set(String file, String type) {
			Filename = file;
			StmtType = type;
		}

	}

	private static class IfStatement extends Statement {
		boolean taken;// 0 for reached, 1 for taken
		LineNumber startLine;
		LineNumber endLine;
		boolean hasElse;
		LineNumber elseStartLine;
		LineNumber elseEndLine;

		IfStatement(boolean _taken, LineNumber _startLine, LineNumber _endLine) {
			taken = _taken;
			startLine = _startLine;
			endLine = _endLine;
		}

		@Override
		public String toString() {
			return "IfStatement [taken=" + taken + ", startLine=" + startLine + ", endLine=" + endLine + ", hasElse="
					+ hasElse + ", elseStartLine=" + elseStartLine + ", elseEndLine=" + elseEndLine + "]";
		}
	}

	private static class WhileStatement extends Statement {
		boolean taken;// 0 for reached, 1 for taken
		boolean firsttaken;
		LineNumber startLine;
		LineNumber endLine;

		WhileStatement(boolean _taken, LineNumber _startLine, LineNumber _endLine) {
			taken = _taken;
			startLine = _startLine;
			endLine = _endLine;
			firsttaken = false;
		}

		@Override
		public String toString() {
			return "WhileStatement [taken=" + taken + ", startLine=" + startLine + ", endLine=" + endLine + "]";
		}
	}

	private static class DoStatement extends Statement {
		boolean taken;// 0 for reached, 1 for taken
		boolean firsttaken;
		LineNumber startLine;
		LineNumber endLine;

		DoStatement(boolean _taken, LineNumber _startLine, LineNumber _endLine) {
			taken = _taken;
			startLine = _startLine;
			endLine = _endLine;
		}

		@Override
		public String toString() {
			return "DoStatement [taken=" + taken + ", startLine=" + startLine + ", endLine=" + endLine + "]";
		}
	}

	private static class VariableDeclaration extends Statement {
		Variable var;
		LineNumber Line;

		VariableDeclaration(Variable _var, LineNumber _Line) {
			var = _var;
			Line = _Line;
		}

		@Override
		public String toString() {
			return "VariableDeclaration [var=" + var + ", Line=" + Line + "]";
		}
	}

	private static class MethodInvoked extends Statement {
		String MethodName;
		Set<Variable> Parameters;
		LineNumber Line;

		MethodInvoked(String _MethodName, Set<Variable> _Parameters, LineNumber _Line) {
			MethodName = _MethodName;
			Parameters = _Parameters;
			Line = _Line;
		}

		@Override
		public String toString() {
			return "MethodInvoked [MethodName=" + MethodName + ", Parameters=" + Parameters + ", Line=" + Line + "]";
		}
	}

	private static class MethodInvocation extends Statement {
		String MethodName;
		Set<Variable> Parameters;
		LineNumber Line;

		MethodInvocation(String _MethodName, Set<Variable> _Parameters, LineNumber _Line) {
			MethodName = _MethodName;
			Parameters = _Parameters;
			Line = _Line;
		}

		@Override
		public String toString() {
			return "MethodInvocation [MethodName=" + MethodName + ", Parameters=" + Parameters + ", Line=" + Line + "]";
		}
	}

	private static class Assignment extends Statement {
		Variable var;
		LineNumber Line;

		Assignment(Variable _var, LineNumber _Line) {
			var = _var;
			Line = _Line;
		}

		@Override
		public String toString() {
			return "Assignment [var=" + var + ", Line=" + Line + "]";
		}
	}

	private static class ReturnStatement extends Statement {
		// TODO : return value
		Variable var;
		LineNumber Line;

		ReturnStatement(Variable _var, LineNumber _Line) {
			var = _var;
			Line = _Line;
		}

		@Override
		public String toString() {
			return "ReturnStatement [var=" + var + ", Line=" + Line + "]";
		}
	}

	public static class LineNumber implements Comparable<LineNumber> {
		int line;
		int addedline;

		public LineNumber(int _line, int _addedline) {
			line = _line;
			addedline = _addedline;
		}

		LineNumber() {
			line = 0;
			addedline = 0;
		}

		public static LineNumber parserLineNumber(String s) {
			int line = Integer.parseInt(s);
			int addedline = 0;
			if (s.indexOf(".") != -1)
				addedline = Integer.parseInt(s.substring(s.indexOf(".") + 1));
			return new LineNumber(line, addedline);
		}

		@Override
		public int compareTo(LineNumber o) {
			if (this.line == o.line) {
				if (this.addedline < o.addedline)
					return -1;
				else if (this.addedline > o.addedline)
					return 1;
				else
					return 0;
			} else {
				if (this.line < o.line)
					return -1;
				else
					return 1;
			}

		}

		@Override
		public String toString() {
			return "LineNumber [line=" + line + ", addedline=" + addedline + "]";
		}
	}

	private static class LineVariables {
		public LineVariables(LineNumber line, Set<Variable> variables) {
			super();
			this.line = line;
			Variables = variables;
		}

		LineNumber line;
		Set<Variable> Variables;

		@Override
		public String toString() {
			return "LineVariables [line=" + line + ", Variables=" + Variables + "]";
		}
	}

	private static class Jump {
		public Jump(LineNumber fromline, LineNumber toline) {
			super();
			this.fromline = fromline;
			this.toline = toline;
		}

		LineNumber fromline;
		LineNumber toline;

	}

	private static class Context {
		Queue<Jump> pendingjumps;
		LineNumber curLine;

		Context(Queue<Jump> _pendingjumps, LineNumber _curLine) {
			pendingjumps = _pendingjumps;
			curLine = _curLine;
		}
	}

	private static class Spectrum implements Cloneable{
		List<LineVariables> values;
		LineNumber curLine;
		Queue<Jump> pendingjumps;
		Stack<Context> contexts;
		Map<Integer, Integer> addedlines;
		List<Integer> deletedlines;// TODO

		public Spectrum clone() {
			Spectrum o = null;
			try {
				o = (Spectrum) super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return o;
		}
		void nextline() {
			if (addedlines.get(curLine.line) == null) {
				curLine.line++;
				return;
			} else {
				if (curLine.addedline == addedlines.get(curLine.line)) {
					curLine.line++;
					curLine.addedline = 0;
				} else {
					curLine.addedline++;
				}
			}
		}

		void runto(LineNumber targetline) {
			//System.out.println("curLine:"+curLine+"\ntargetLine:"+targetline);
			LineVariables last = values.get(values.size() - 1);
			do {
				// if ((pendingjumps.isEmpty() || curLine >
				// pendingjumps.peek().fromline) && curLine > targetline) {
				if ((pendingjumps.isEmpty() || curLine.compareTo(pendingjumps.peek().fromline) < 0)
						&& curLine.compareTo(targetline) > 0) {
					break;
					// throw new Exception("wild line");
				}
				if (!pendingjumps.isEmpty() && pendingjumps.peek().fromline == curLine) {
					curLine = pendingjumps.poll().toline;
				} else {
					// curLine++;
					nextline();
				}
				values.add(new LineVariables(curLine, last.Variables));
			} while (curLine.compareTo(targetline)!=0);
			//System.out.println("after:curLine:"+curLine+"\ntargetLine:"+targetline);
		}

		Spectrum() {
			values = new ArrayList<LineVariables>();
			pendingjumps = new LinkedList<Jump>();
			contexts = new Stack<Context>();
			contexts.push(new Context(new LinkedList<Jump>(), new LineNumber()));
		}

		Spectrum(Map<Integer, Integer> _addedlines, List<Integer> _deletedlines) {
			values = new ArrayList<LineVariables>();
			pendingjumps = new LinkedList<Jump>();
			contexts = new Stack<Context>();
			contexts.push(new Context(new LinkedList<Jump>(), new LineNumber()));
			addedlines = _addedlines;
			deletedlines = _deletedlines;
		}

		void form(List<Statement> Stmts) {
			values = new ArrayList<LineVariables>();
			// pendingjumps = new LinkedList<Jump>();
			pendingjumps = contexts.peek().pendingjumps;
			curLine = contexts.peek().curLine;
			Iterator<Statement> it = Stmts.iterator();
			while (it.hasNext()) {
				Statement st = it.next();

				if (st instanceof IfStatement) {
					LineNumber t = ((IfStatement) st).startLine;
					runto(t);
					if (!((IfStatement) st).taken) {
						if (!((IfStatement) st).hasElse)
							pendingjumps.offer(new Jump(curLine, ((IfStatement) st).endLine));
						// curLine = ((IfStatement) st).endLine;
						else {
							pendingjumps.offer(new Jump(curLine, ((IfStatement) st).elseStartLine));
							// curLine = ((IfStatement) st).elseStartLine;
						}
					} else {
						pendingjumps.offer(new Jump(curLine, ((IfStatement) st).startLine));
						// curLine=(((IfStatement) st).startLine);
					}
				}
				if (st instanceof WhileStatement) {
					LineNumber t = ((WhileStatement) st).startLine;
					if (!((WhileStatement) st).taken) {
						pendingjumps.offer(new Jump(curLine, ((WhileStatement) st).endLine));
						runto(t);
					} else {
						if (((WhileStatement) st).firsttaken) {
							runto(t);
						} else {
							pendingjumps
									.offer(new Jump(((WhileStatement) st).endLine, ((WhileStatement) st).startLine));
							runto(t);
						}

					}
				}
				if (st instanceof DoStatement) {
					LineNumber t = ((DoStatement) st).startLine;
					if (((DoStatement) st).firsttaken) {
						System.out.println("Dostmt 1st taken");
						runto(t);
					} else {
						System.out.println("Dosymt taken");
						pendingjumps.offer(new Jump(((DoStatement) st).endLine, ((DoStatement) st).startLine));
						runto(t);
					}
				}
				if (st instanceof VariableDeclaration) {
					LineNumber t = ((VariableDeclaration) st).Line;
					runto(t);
					Set<Variable> tmp = new TreeSet<Variable>();
					tmp.addAll(values.get(values.size() - 1).Variables);
					tmp.add(((VariableDeclaration) st).var);
					values.remove(values.size() - 1);
					values.add(new LineVariables(t, tmp));
				}
				if (st instanceof MethodInvoked) {
					LineNumber t = ((MethodInvoked) st).Line;

					contexts.push(new Context(new LinkedList<Jump>(), t));
					pendingjumps = contexts.peek().pendingjumps;
					curLine = contexts.peek().curLine;
					Set<Variable> tmp = new TreeSet<Variable>();
					tmp.addAll(((MethodInvoked) st).Parameters);
					values.add(new LineVariables(t, tmp));
				}
				if (st instanceof Assignment) {
					LineNumber t = ((Assignment) st).Line;
					runto(t);
					LineVariables tmp = values.get(values.size() - 1);
					Set<Variable> add = new TreeSet<Variable>();
					String s = ((Assignment) st).var.Name;
					boolean flag = true;
					for (Variable v : tmp.Variables) {
						if (v.Name == s) {
							flag = false;
							add.add(((Assignment) st).var);
						} else
							add.add(v);
					}
					if (flag) {
						add.add(((Assignment) st).var);
					}
					values.remove(values.size() - 1);
					values.add(new LineVariables(t, add));
					curLine = t;
				}
				if (st instanceof ReturnStatement) {
					LineNumber t = ((ReturnStatement) st).Line;
					runto(t);
					contexts.pop();
					pendingjumps = contexts.peek().pendingjumps;
					curLine = contexts.peek().curLine;
				}
				if (st instanceof MethodInvocation) {

				}
				// System.out.println(values);
				/*
				 * System.out.println(st); System.out.println(curLine); for
				 * (LineVariables i : values) { System.out.println("Line " +
				 * i.line + ": " + i.Variables); }
				 */
			}
			for (LineVariables i : values) {
				// if(verbose)
				//System.out.println("Line " + i.line + ": " + i.Variables);
			}
		}

		public static class Mode {
			public enum ModeEnum {
				Default, LCS, LCS_Bestfit;
			}

			ModeEnum mode;
			double varw;
			double sizediffw;
			double linediffw;

			Mode(ModeEnum _mode, double _varw, double _sizediffw, double _linediffw) {
				mode = _mode;
				varw = _varw;
				sizediffw = _sizediffw;
				linediffw = _linediffw;
			}
		}

		public double diff(Spectrum spec2, Mode diffmode) {
			double ret = 0;
			Iterator<LineVariables> it1 = values.iterator(), it2 = spec2.values.iterator();
			int min = spec2.values.size() < values.size() ? spec2.values.size() : values.size();
			int max = spec2.values.size() > values.size() ? spec2.values.size() : values.size();
			ret += (max - min) * diffmode.sizediffw;
			switch (diffmode.mode) {
			case Default:
				while (it1.hasNext() && it2.hasNext()) {
					LineVariables l1 = it1.next(), l2 = it2.next();
					if (l1.line.compareTo(l2.line)!=0)
						ret += diffmode.linediffw;
					else {
						if (!l1.Variables.equals(l2.Variables))
							ret += diffmode.varw;
					}
				}
				break;
			case LCS:
				int f[][] = new int[values.size()][spec2.values.size()];
				int prev[][] = new int[values.size()][spec2.values.size()];
				for (int i = 1; it1.hasNext(); i++) {
					LineVariables l1 = it1.next();
					for (int j = 1; it2.hasNext(); j++) {
						LineVariables l2 = it2.next();
						if (l1.line.compareTo(l2.line)==0) {
							prev[i][j] = 0;
							f[i][j] = f[i - 1][j - 1] + 1;
						} else if (f[i - 1][j] <= f[i][j - 1]) {// 优先让spec2失配
							f[i][j] = f[i][j - 1];
							prev[i][j] = 2;
						} else {
							f[i][j] = f[i - 1][j];
							prev[i][j] = 1;
						}
					}
				}
				ret += (min - f[values.size()][spec2.values.size()]) * diffmode.linediffw;
				for (int i = values.size(), j = spec2.values.size();;) {
					if (i == 0 || j == 0)
						break;
					if (prev[i][j] == 0) {
						int neq = values.get(i - 1).Variables.equals(spec2.values.get(j - 1).Variables) ? 0 : 1;
						ret += neq * diffmode.varw;
						i--;
						j--;
					} else if (prev[i][j] == 1)
						i--;
					else if (prev[i][j] == 2)
						j--;
				}
				break;
			case LCS_Bestfit:
				f = new int[values.size()][spec2.values.size()];
				prev = new int[values.size()][spec2.values.size()];
				double b[][] = new double[values.size()][spec2.values.size()];
				for (int i = 1; it1.hasNext(); i++) {
					LineVariables l1 = it1.next();
					for (int j = 1; it2.hasNext(); j++) {
						LineVariables l2 = it2.next();
						if (l1.line.compareTo(l2.line)==0) {
							prev[i][j] = 0;
							f[i][j] = f[i - 1][j - 1] + 1;
							int neq = values.get(i - 1).Variables.equals(spec2.values.get(j - 1).Variables) ? 0 : 1;
							b[i][j] = b[i - 1][j - 1] + neq * diffmode.varw;
							if (f[i - 1][j] == f[i][j] && b[i - 1][j] < b[i][j]) {
								f[i][j] = f[i - 1][j];
								b[i][j] = b[i - 1][j];
								prev[i][j] = 1;
							}
							if (f[i][j - 1] == f[i][j] && b[i][j - 1] < b[i][j]) {
								f[i][j] = f[i][j - 1];
								b[i][j] = b[i][j - 1];
								prev[i][j] = 2;
							}
						} else if (f[i - 1][j] == f[i][j - 1]) {
							if (b[i - 1][j] < b[i][j - 1]) {
								f[i][j] = f[i - 1][j];
								b[i][j] = b[i - 1][j];
								prev[i][j] = 1;
							} else {
								f[i][j] = f[i][j - 1];
								b[i][j] = b[i][j - 1];
								prev[i][j] = 2;
							}
						} else if (f[i - 1][j] < f[i][j - 1]) {
							f[i][j] = f[i][j - 1];
							prev[i][j] = 2;
						} else {
							f[i][j] = f[i - 1][j];
							prev[i][j] = 1;
						}
					}
				}
				ret += (min - f[values.size()][spec2.values.size()]) * diffmode.linediffw;
				for (int i = values.size(), j = spec2.values.size();;) {
					if (i == 0 || j == 0)
						break;
					if (prev[i][j] == 0) {
						int neq = values.get(i - 1).Variables.equals(spec2.values.get(j - 1).Variables) ? 0 : 1;
						ret += neq * diffmode.varw;
						i--;
						j--;
					} else if (prev[i][j] == 1)
						i--;
					else if (prev[i][j] == 2)
						j--;
				}
				break;
			default:
			}
			return ret;
		}
	}

	public static LineNumber getLine(String s) {
		// return Integer.parseInt(s.substring(s.indexOf(":") + 1));/
		return LineNumber.parserLineNumber(s.substring(s.indexOf(":") + 1));
	}

	public static String getFile(String s) {
		return s.substring(s.indexOf(":") + 1);
	}

	public static void getElseLines(String t, IfStatement st) {
		if (t.startsWith(" "))
			t = t.substring(1);
		// System.out.println("getElseLines from \"" + t + "\"");
		if (t.equals("Else:null"))
			st.hasElse = false;
		else {
			st.hasElse = true;
			st.elseStartLine = LineNumber.parserLineNumber(t.substring(t.indexOf(":") + 1, t.indexOf(" ")));
			st.elseEndLine = LineNumber.parserLineNumber(t.substring(t.indexOf(" ", t.indexOf(" ") + 1) + 1));
		}
	}

	public static void getBranchLines(String t, IfStatement st) {
		if (t.startsWith(" "))
			t = t.substring(1);
		// System.out.println("getBranchLines from \"" + t + "\"");
		st.startLine = LineNumber.parserLineNumber(t.substring(t.indexOf(":") + 1, t.indexOf(" ")));
		st.endLine = LineNumber.parserLineNumber(t.substring(t.indexOf(" ", t.indexOf(" ") + 1) + 1));
	}

	public static void getBranchLines(String t, WhileStatement st) {
		if (t.startsWith(" "))
			t = t.substring(1);
		// System.out.println("getBranchLines from \"" + t + "\"");
		st.startLine = LineNumber.parserLineNumber(t.substring(t.indexOf(":") + 1, t.indexOf(" ")));
		st.endLine = LineNumber.parserLineNumber(t.substring(t.indexOf(" ", t.indexOf(" ") + 1) + 1));
	}
	public static void getBranchLines(String t, DoStatement st) {
		if (t.startsWith(" "))
			t = t.substring(1);
		// System.out.println("getBranchLines from \"" + t + "\"");
		st.startLine = LineNumber.parserLineNumber(t.substring(t.indexOf(":") + 1, t.indexOf(" ")));
		st.endLine = LineNumber.parserLineNumber(t.substring(t.indexOf(" ", t.indexOf(" ") + 1) + 1));
	}

	public static void getScope(String t, VariableDeclaration st) {
		//System.out.println(t);
		if (t.startsWith(" "))
			t = t.substring(1);
		// System.out.println("getBranchLines from \"" + t + "\"");
		st.var.scopestartLine = LineNumber.parserLineNumber(t.substring(t.indexOf(":") + 1, t.indexOf(" ")));
		st.var.scopeendLine = LineNumber.parserLineNumber(t.substring(t.indexOf(" ", t.indexOf(" ") + 1) + 1));
	}

	public static Variable getVariable(Scanner sc) {
		String t1 = sc.next();
		String t2 = sc.next();
		// System.out.println("t1"+t1+"t2"+t2);
		String type = t2.substring(t2.indexOf(':') + 1);
		Scanner sc2 = new Scanner(t1).useDelimiter("=");
		Variable ret = null;
		String name = sc2.next();
		String value = sc2.next();
		switch (type) {
		case "int":
			if (value.equals("Uninitialized")) {
				ret = new VarInt(name, type, 0);
				ret.Defined = false;
			} else {
				ret = new VarInt(name, type, Integer.parseInt(value));
				ret.Defined = true;
			}
			break;
		case "short":
			if (value.equals("Uninitialized")) {
				ret = new VarShort(name, type, (short) 0);
				ret.Defined = false;
			} else {
				ret = new VarShort(name, type, Short.parseShort(value));
				ret.Defined = true;
			}
			break;
		case "long":
			if (value.equals("Uninitialized")) {
				ret = new VarLong(name, type, 0);
				ret.Defined = false;
			} else {
				ret = new VarLong(name, type, Long.parseLong(value));
				ret.Defined = true;
			}
			break;
		case "byte":
			if (value.equals("Uninitialized")) {
				ret = new VarByte(name, type, (byte) 0);
				ret.Defined = false;
			} else {
				ret = new VarByte(name, type, Byte.parseByte(value));
				ret.Defined = true;
			}
			break;
		case "Object":
			if (value.equals("Uninitialized")) {
				ret = new VarObject(name, type, 0);
				ret.Defined = false;
			} else {
				ret = new VarObject(name, type, value);
				ret.Defined = true;
			}
			break;
		case "char":
			if (value.equals("Uninitialized")) {
				ret = new VarChar(name, type, (char) 0);
				ret.Defined = false;
			} else {
				ret = new VarChar(name, type, value.charAt(0));
				ret.Defined = true;
			}
			break;
		case "boolean":
			if (value.equals("Uninitialized")) {
				ret = new VarBoolean(name, type, false);
				ret.Defined = false;
			} else {
				ret = new VarBoolean(name, type, Boolean.parseBoolean(value));
				ret.Defined = true;
			}
			break;
		case "float":
			if (value.equals("Uninitialized")) {
				ret = new VarFloat(name, type, 0);
				ret.Defined = false;
			} else {
				ret = new VarFloat(name, type, Float.parseFloat(value));
				ret.Defined = true;
			}
			break;
		case "double":
			if (value.equals("Uninitialized")) {
				ret = new VarDouble(name, type, 0);
				ret.Defined = false;
			} else {
				ret = new VarDouble(name, type, Double.parseDouble(value));
				ret.Defined = true;
			}
			break;
		case "string":
			if (value.equals("Uninitialized")) {
				ret = new VarString(name, type, "");
				ret.Defined = false;
			} else {
				ret = new VarString(name, type, value);
				ret.Defined = true;
			}
			break;
		}
		sc2.close();
		// System.out.println(ret);
		return ret;
	}

	public static Statement getStatement(String src) {
		Scanner sc = new Scanner(src);
		String label = sc.next();
		Scanner labelsc = new Scanner(label.substring(1, label.indexOf(">"))).useDelimiter(",");
		String type = labelsc.next();

		Statement ret = null;
		String file = null;
		// System.out.println(type);
		switch (type) {
		case "Method_invoked":
			String funcname = labelsc.next();
			int parac = labelsc.nextInt();
			labelsc.close();
			labelsc = new Scanner(sc.next()).useDelimiter(",");
			Set<Variable> Parameters = new TreeSet<Variable>();
			for (int i = 0; i < parac; i++) {
				Variable par = getVariable(labelsc);
				Parameters.add(par);
			}
			LineNumber line = getLine(labelsc.next());
			file = getFile(labelsc.next());
			ret = new MethodInvoked(funcname, Parameters, line);
			break;
		case "MethodInvocation":
			funcname = labelsc.next();
			parac = labelsc.nextInt();
			labelsc.close();
			labelsc = new Scanner(sc.next()).useDelimiter(",");
			Parameters = new TreeSet<Variable>();
			for (int i = 0; i < parac; i++) {
				Variable par = getVariable(labelsc);
				Parameters.add(par);
			}
			line = getLine(labelsc.next());
			file = getFile(labelsc.next());
			ret = new MethodInvoked(funcname, Parameters, line);
			break;
		case "IfStatement":
			String temp = labelsc.next();
			boolean taken = (temp.equals("taken") ? true : false);
			sc.useDelimiter(",");
			ret = new IfStatement(taken, null, null);
			getBranchLines(sc.next(), (IfStatement) ret);
			getElseLines(sc.next(), (IfStatement) ret);
			file = getFile(sc.next());
			break;
		case "DoStatement":
			temp = labelsc.next();
			taken = (temp.equals("taken") ? true : false);
			ret = new DoStatement(taken, null, null);
			sc.useDelimiter(",");
			getBranchLines(sc.next(), (DoStatement) ret);
			file = getFile(sc.next());
			break;
		case "WhileStatement":
			temp = labelsc.next();
			taken = (temp.equals("taken") ? true : false);
			ret = new WhileStatement(taken, null, null);
			sc.useDelimiter(",");
			getBranchLines(sc.next(), (WhileStatement) ret);
			file = getFile(sc.next());
			break;
		case "Assignment":
			labelsc.close();
			labelsc = new Scanner(sc.next()).useDelimiter(",");
			Variable var = getVariable(labelsc);
			line = getLine(labelsc.next());
			file = getFile(labelsc.next());
			ret = new Assignment(var, line);
			break;
		case "ReturnStatement":
			labelsc.close();
			labelsc = new Scanner(sc.next()).useDelimiter(",");
			//var = getVariable(labelsc);
			line = getLine(labelsc.next());
			file = getFile(labelsc.next());
			ret = new ReturnStatement(null, line);
			break;
		case "VariableDeclaration":
			labelsc.close();
			labelsc = sc.useDelimiter(",");
			var = getVariable(labelsc);
			line = getLine(labelsc.next());
			ret = new VariableDeclaration(var, line);
			getScope(labelsc.next(), (VariableDeclaration) ret);
			file = getFile(labelsc.next());
			break;
		}
		labelsc.close();
		sc.close();

		ret.set(file, type);
		// System.out.println(ret);
		return ret;
	}

	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();

		return fileData.toString();
	}

	public static List<Statement> parsetrace(BufferedReader reader,String delimiter) throws IOException {
		List<Statement> Stmts = new ArrayList<Statement>();
		// BufferedReader reader = new BufferedReader(new FileReader(Filename));
		String str = null;
		while ((str = reader.readLine()) != null) {
			//System.out.println(str);
			if(str.startsWith(delimiter))break;
			//System.out.println("not ****");
			Statement st = getStatement(str);
			//System.out.println(st);
			if (st instanceof IfStatement) {
				if (((IfStatement) st).taken) {
					Stmts.remove(Stmts.size() - 1);
				}
			}
			if (st instanceof WhileStatement) {
				if (((WhileStatement) st).taken) {
					Statement s = Stmts.get(Stmts.size() - 1);
					if (s instanceof WhileStatement) {
						if (((WhileStatement) s).startLine == ((WhileStatement) st).startLine
								&& !((WhileStatement) s).taken) {
							Stmts.remove(Stmts.size() - 1);
							((WhileStatement) st).firsttaken = true;
						}
					}
				}
			}
			if (st instanceof DoStatement) {
				if (((DoStatement) st).taken) {
					Statement s = Stmts.get(Stmts.size() - 1);
					if (s instanceof DoStatement) {
						if (((DoStatement) s).startLine == ((DoStatement) st).startLine && !((DoStatement) s).taken) {
							Stmts.remove(Stmts.size() - 1);
							((DoStatement) st).firsttaken = true;
						}
					}
				}
			}
			Stmts.add(st);
		}
		/*
		 * for (Statement st : Stmts) { System.out.println(st); }
		 */
		return Stmts;
	}

	public static Spectrum parseheader(BufferedReader reader) throws IOException {
		// TODO
		Map<Integer, Integer> addedlines = new TreeMap<Integer, Integer>();
		List<Integer> deletedlines = new LinkedList<Integer>();
		return new Spectrum(addedlines, deletedlines);
	}

	public static void main(String args[]) {
		/*System.out.println("Spec1:");
		BufferedReader reader = null;
		Spectrum spec1 = null, spec2 = null;
		try {
			String testf = "/home/akarin/workspace/test/test1.txt";
			reader = new BufferedReader(new FileReader(testf));
			spec1 = parseheader(reader);
			spec1.form(parsetrace(reader,null));
		} catch (IOException e) {
			System.out.println("parse Tracefile1 failed");
			e.printStackTrace();
		}
		System.out.println("done:");*/
		String tracefile1 = "/home/akarin/workspace/test/randomtest/trace1.txt";
		String tracefile2 = "/home/akarin/workspace/test/randomtest/trace2.txt";
		parser.process(new String[] { tracefile1, tracefile2, "-D", "**************", "-r", String.valueOf(11) });
	}

	public static double process(String args[]) {
		
		CommandLineParser cmdlparser = new DefaultParser();
		Options options = new Options();
		options.addOption("D", "Delimiter", true, "Delimiter");
		options.addOption("r", "repeats",true,"how many test cases in 1 tracefile");
		CommandLine commandLine = null;
		try {
			commandLine = cmdlparser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String TraceFile1 = "", TraceFile2 = "";
		TraceFile1 = args[0];
		TraceFile2 = args[1];
		String delimiter = null;
		int times = 1;
		
		if (commandLine.hasOption('D')) {
			delimiter = commandLine.getOptionValue('D');
		}
		if(commandLine.hasOption('r'))
		{
			times = Integer.parseInt(commandLine.getOptionValue('r'));
		}
		

		//System.out.println("Spec1:");
		BufferedReader reader = null;
		List<Spectrum> list1 = new LinkedList<Spectrum>(), list2 = new LinkedList<Spectrum>();
		Spectrum spec1 = null, spec2 = null;
		try {
			reader = new BufferedReader(new FileReader(TraceFile1));
			spec1 = parseheader(reader);
			for(int i=1;i<=times;i++)
			{
				Spectrum tempspec = spec1.clone();
				tempspec.form(parsetrace(reader,delimiter));
				list1.add(tempspec);
			}
			
		} catch (IOException e) {
			System.out.println("parse Tracefile1 failed");
			e.printStackTrace();
		}

		//System.out.println("Spec2:");
		try {
			reader = new BufferedReader(new FileReader(TraceFile2));
			spec2 = parseheader(reader);
			for(int i=1;i<=times;i++)
			{
				Spectrum tempspec = spec2.clone();
				tempspec.form(parsetrace(reader,delimiter));
				list2.add(tempspec);
			}
		} catch (IOException e) {
			System.out.println("parse Tracefile2 failed");
			e.printStackTrace();
		}

		//double ret = spec1.diff(spec2, new Spectrum.Mode(Spectrum.Mode.ModeEnum.Default, 0.2, 1, 2));
		double ret = 0;
		int maxi = 0;
		List<Double> distance = new LinkedList<Double>();
		for(int i=0;i<times;i++)
		{
			double tmp;
			tmp = list1.get(i).diff(list2.get(i),new Spectrum.Mode(Spectrum.Mode.ModeEnum.Default, 0.2, 1, 2));
			System.out.println(tmp);
			distance.add(new Double(tmp));
			if(tmp>ret)
			{
				ret = tmp;
				maxi = i;
			}
		}
		//System.out.println(ret);
		if(maxi>0)
			System.out.println("testcase "+String.valueOf(maxi-1)+" has the longest distance "+String.valueOf(ret));
		else
			System.out.println("the original test case has the longest distance "+String.valueOf(ret));
		return ret;
	}

}
