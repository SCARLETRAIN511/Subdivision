import org.smurn.jply.PlyReaderFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author tangshao
 */
public class MainEntry {
    public static void compare(InputModel inputModel1, InputModel inputModel2) throws IOException {
        //write the haus
        ComparisonStep.writeHausorffDistribution(inputModel2.getVertices(), inputModel1.getVertices());
        ComparisonStep.writeAngle(inputModel2);
        ComparisonStep.writeCurvature1(inputModel2);
        ComparisonStep.writeCurvature2(inputModel2);

    }

    public static void main(String[] args) throws IOException {
        //set the current timeMills
        long startTime = System.currentTimeMillis();

        //file location
        String modelName = "cow";
        String fileName = "C:\\Users\\tangj\\Downloads\\" + modelName + ".ply";

        //Variables initializing
        InputStream in = new FileInputStream(fileName);
        PlyReaderFile reader = new PlyReaderFile(in);
        int numFaces = reader.getElementCount("face");
        int numVertices = reader.getElementCount("vertex");
        Map<Integer, Vector3d> vertices = new HashMap<>(numVertices);
        Map<Integer, List<Integer>> faces = new HashMap<>(numFaces);
        //read the detail
        ReadPLY.read(reader, vertices, faces);
        System.out.println("--------Input model read successfully-------");
        System.out.println("Info of the old model");
        System.out.println("Number of elements:" + numFaces);
        System.out.println("Number of vertices:" + numVertices);


        //start implementing the algorithms on the data structure
        AnalysisStep analysisStep = new AnalysisStep(vertices, faces);
        InputModel inputModel = analysisStep.createTheModel();
        analysisStep.implementScheme1(inputModel);
        //analysisStep.implementScheme2(analysisStep.createTheModel());

        System.out.println("--------Calculate the normal for the vertex-------");
        Map<Integer, Vector3d> normalMap = ComparisonStep.getNormalForVertices(analysisStep.createTheModel());
        OutputModel outputModel = new OutputModel(analysisStep.getVertexMap(), analysisStep.getFaceMap(), normalMap);
        System.out.println("-------Subdivision scheme implemented successfully-------");
        System.out.println("Info of the new model:");
        System.out.println("Number of elements:" + outputModel.getFaceMap().size());
        System.out.println("Number of vertices:" + outputModel.getVertexMap().size());

        //write the file
        outputModel.writePLYNormal(modelName + "_refined");
        long endTime = System.currentTimeMillis();

        //print out the running time
        System.out.println("-------File written successfully-------");
        System.out.println("The program takes " + (endTime - startTime) / 1000d + "s");

        //comparison
        System.out.println("-------Start doing the comparison-------");
        compare(inputModel, analysisStep.createTheModel());
        System.out.println("-------The whole process finished-------");
    }
}
