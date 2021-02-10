package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

public class LSystemBuilderImpl implements LSystemBuilder {

	public class LSystemImpl implements LSystem {

		/**
		 * Method creates new Context and TurtleState which it then pushes and 
		 * calls method generate(). It then executes the commands contained in generated string.
		 * @param arg0 level of fractal to be generated
		 * @param arg1 Painter used for displaying fractals
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			
			Context context = new Context(); 
			TurtleState turtleState = new TurtleState(origin, 
					new Vector2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))),
							Color.black, unitLength * Math.pow(unitLengthDegreeScaler, arg0)); 
			context.pushState(turtleState);
			
			String generatedString = generate(arg0); 
			
			char[] array = generatedString.toCharArray(); 
			for (char c : array) {
				if (actions.get(c) != null) {
					actions.get(c).execute(context, arg1); 
				} 
			}
			
		}

		/**
		 * Method produces string which is result of applying production <code>arg0</code> times.
		 * @param arg0 level of fractal to be generated
		 */
		@Override
		public String generate(int arg0) {
			StringBuilder sb = new StringBuilder(); 
			 
			if (arg0 == 0) {
				return axiom;
			}

			char[] workingArray = axiom.toCharArray(); 
			for (int i = 0; i < arg0; i++) {
				sb = new StringBuilder(); 
				for (int j = 0; j < workingArray.length; j++) {
					if (productions.get(workingArray[j]) != null) {
						sb.append(productions.get(workingArray[j]));
					} else {
						sb.append(workingArray[j]); 
					}
				}
				workingArray = sb.toString().toCharArray(); 
			}
			
			return sb.toString();
		}
		
	}
	
	private Dictionary<Character, String> productions = new Dictionary<>(); 
	private Dictionary<Character, Command> actions = new Dictionary<>(); 
	private double unitLength = 0.1; 
	private double unitLengthDegreeScaler = 1.0; 
	private Vector2D origin = new Vector2D(0,0);
	private double angle = 0; 
	private String axiom = ""; 
	
	
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Method configures LSystemBuilder's attributes by parsing array of Strings
	 * @param arg0 string array to be parsed
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for (String s : arg0) {
			String[] strings = s.split("\\s+"); 
			switch (strings[0]) {
				case "origin": 
					setOrigin(Double.parseDouble(strings[1]), Double.parseDouble(strings[2])); 
					break;
				case "angle": 
					setAngle(Double.parseDouble(strings[1]));
					break; 
				case "unitLength":
					setUnitLength(Double.parseDouble(strings[1]));
					break;
				case "unitLengthDegreeScaler":			
					setUnitLengthDegreeScaler(Double.parseDouble(strings[1]) /
											  Double.parseDouble(strings[3]));
					break;
				case "command":
					if (strings.length == 4) 
						registerCommand(strings[1].charAt(0), strings[2] + " " + strings[3]);
					if (strings.length == 3) 
						registerCommand(strings[1].charAt(0), strings[2]);
					break;
				case "axiom":
					this.axiom = strings[1]; 
					break;
				case "production": 
					productions.put(strings[1].charAt(0), strings[2]);
					break;
			}
		}
		return this;
	}

	/**
	 * Method puts given command into Dictionary. 
	 * @param arg0 char which represents command
	 * @param arg1 string which contains command name and value
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String[] strings = arg1.split("\\s+"); 
		Command command = null; 
		switch (strings[0]) {
			case "draw": 
				command = new DrawCommand(Double.parseDouble(strings[1])); 
				break; 
			case "skip":
				command = new SkipCommand(Double.parseDouble(strings[1])); 
				break; 
			case "scale":
				command = new ScaleCommand(Double.parseDouble(strings[1])); 
				break;
			case "rotate": 
				command = new RotateCommand(Double.parseDouble(strings[1])); 
				break; 
			case "color":
				command = new ColorCommand(Color.decode("#" + strings[1])); 
				break; 
			case "push":
				command = new PushCommand(); 
				break;
			case "pop":
				command = new PopCommand(); 
				break; 			
		}
		actions.put(arg0, command); 
		
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0; 
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		axiom = arg0; 
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1); 
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0; 
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0; 
		return this;
	}

}
