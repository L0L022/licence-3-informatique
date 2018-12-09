package spanning_tree;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Main {
	private static boolean useGridGraph = true;
	private static boolean useCompleteGraph = true;
	private static boolean useErdosRenyiGraph = true;
	private static boolean useLollipopGraph = true;

	private static boolean showGridGraph = true;

	private static boolean useBFS = true;
	private static boolean useKruskal = true;
	private static boolean useRandomWeight = true;
	private static boolean useRandomEdge = true;
	private static boolean useAldousBroder = true;
	private static boolean useWilson = true;

	private static List<MinimumWeightSpanningTreeGenerator> treeGenerators;
	private static MinimumWeightSpanningTreeGenerator currentTreeGenerator;

	private static Writer output = null;

	private static List<List<Stats>> treeGeneratorsStats;

	public static void main(String argv[]) {
		initTreeGenerators();
		treeGeneratorsStats = new ArrayList<>();

		for (MinimumWeightSpanningTreeGenerator treeGenerator : treeGenerators) {
			currentTreeGenerator = treeGenerator;

			System.out.println(currentTreeGenerator.name());
			treeGeneratorsStats.add(showStats());
		}

		int i = 0;
		for (MinimumWeightSpanningTreeGenerator treeGenerator : treeGenerators) {
			currentTreeGenerator = treeGenerator;

			try {
				output = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(currentTreeGenerator.name() + ".csv"), "utf-8"));

				output.write("Stats,");
				if (useGridGraph) {
					output.write("Grid,");
				}
				if (useCompleteGraph) {
					output.write("Complete graph,");
				}
				if (useErdosRenyiGraph) {
					output.write("Erdos-Renyi graph,");
				}
				if (useLollipopGraph) {
					output.write("Lollipop graph,");
				}
				output.write("\n");

				String[] statsName = { "Average eccentricity", "Average wiener index", "Average diameter",
						"Average number of leaves", "Average number of degree 2 vertices",
						"Average computation time(ms)" };

				for (int stat = 0; stat < statsName.length; ++stat) {
					output.write(statsName[stat] + ",");

					for (Stats stats : treeGeneratorsStats.get(i)) {
						switch (stat) {
						case 0:
							output.write(Double.toString(stats.averageEccentricity) + ",");
							break;
						case 1:
							output.write(Double.toString(stats.averageWienerIndex) + ",");
							break;
						case 2:
							output.write(Double.toString(stats.averageDiameter) + ",");
							break;
						case 3:
							output.write(Double.toString(stats.averageNumberOfLeaves) + ",");
							break;
						case 4:
							output.write(Double.toString(stats.averageNumberOfDegreeTwoVertices) + ",");
							break;
						case 5:
							output.write(Double.toString(stats.averageComputationTimeMilliSec) + ",");
							break;
						}
					}

					output.write("\n");
				}

			} catch (IOException ex) {
				// Report
			} finally {
				try {
					output.close();
				} catch (Exception ex) {
					/* ignore */}
			}
			++i;
		}
	}

	private static void initTreeGenerators() {
		treeGenerators = new ArrayList<>();

		if (useBFS) {
			treeGenerators.add(new BreadthFirstSearch());
		}

		if (useKruskal) {
			treeGenerators.add(new Kruskal());
		}

		if (useRandomWeight) {
			treeGenerators.add(new RandomWeight());
		}

		if (useRandomEdge) {
			treeGenerators.add(new RandomEdge());
		}

		if (useAldousBroder) {
			treeGenerators.add(new AldousBroder());
		}

		if (useWilson) {
			treeGenerators.add(new Wilson());
		}
	}

	private static List<Stats> showStats() {
		List<Stats> stats = new ArrayList<>();

		if (useGridGraph) {
			Grid grid = new Grid(1920 / 11, 1080 / 11);
			System.out.println("Grid height: " + grid.height + " width: " + grid.width);
			stats.add(showStats(grid.graph, grid));
		}

		if (useCompleteGraph) {
			System.out.println("Complete graph");
			stats.add(showStats(new Complete(400).graph, null));
		}

		if (useErdosRenyiGraph) {
			System.out.println("Erdos-Renyi graph");
			stats.add(showStats(new ErdosRenyi(1_000, 100).graph, null));
		}

		if (useLollipopGraph) {
			System.out.println("Lollipop graph");
			stats.add(showStats(new Lollipop(1_000).graph, null));
		}

		return stats;
	}

	private static Stats showStats(Graph graph, Grid grid) {
		int nbrOfSamples = 10;
		int diameterSum = 0;
		double eccentricitySum = 0;
		long wienerSum = 0;
		int degreesSum[] = { 0, 0, 0, 0, 0 };
		int degrees[];

		List<Edge> randomTree = null;
		RootedTree rooted = null;

		long startingTime = System.nanoTime();
		for (int i = 0; i < nbrOfSamples; i++) {
			randomTree = currentTreeGenerator.generateTree(graph);

			rooted = new RootedTree(randomTree, 0);
			// rooted.printStats();
			diameterSum = diameterSum + rooted.getDiameter();
			eccentricitySum = eccentricitySum + rooted.getAverageEccentricity();
			wienerSum = wienerSum + rooted.getWienerIndex();

			degrees = rooted.getDegreeDistribution(4);
			for (int j = 1; j < 5; j++) {
				degreesSum[j] = degreesSum[j] + degrees[j];
			}
		}
		long delay = System.nanoTime() - startingTime;

		System.out.println("On " + nbrOfSamples + " samples:");
		System.out.println("Average eccentricity: " + (eccentricitySum / nbrOfSamples));
		System.out.println("Average wiener index: " + (wienerSum / nbrOfSamples));
		System.out.println("Average diameter: " + (diameterSum / nbrOfSamples));
		System.out.println("Average number of leaves: " + (degreesSum[1] / nbrOfSamples));
		System.out.println("Average number of degree 2 vertices: " + (degreesSum[2] / nbrOfSamples));
		System.out.println("Average computation time: " + delay / (nbrOfSamples * 1_000_000) + "ms");

		Stats stats = new Stats();
		stats.averageEccentricity = eccentricitySum / nbrOfSamples;
		stats.averageWienerIndex = wienerSum / nbrOfSamples;
		stats.averageDiameter = diameterSum / nbrOfSamples;
		stats.averageNumberOfLeaves = degreesSum[1] / nbrOfSamples;
		stats.averageNumberOfDegreeTwoVertices = degreesSum[2] / nbrOfSamples;
		stats.averageComputationTimeMilliSec = delay / (nbrOfSamples * 1_000_000);

		if (grid != null && showGridGraph) {
			try {
				showGrid(grid, rooted, randomTree);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return stats;
	}

	private static void showGrid(Grid grid, RootedTree rooted, List<Edge> randomTree) throws InterruptedException {
		JFrame window = new JFrame("solution");
		final Labyrinth laby = new Labyrinth(grid, rooted);

		laby.setStyleBalanced();
		// laby.setShapeBigNodes();
		// laby.setShapeSmallAndFull();
		laby.setShapeSmoothSmallNodes();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(laby);
		window.pack();
		window.setLocationRelativeTo(null);

		for (final Edge e : randomTree) {
			laby.addEdge(e);
		}
		laby.drawLabyrinth();

		window.setVisible(true);

		// Pour générer un fichier image.
		// try {
		// laby.saveImage("resources/random.png");
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }

	}
}
