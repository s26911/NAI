import java.util.Arrays;

public class Main {
    private final static String trainingDataDir = "TrainingData";

    public static void main(String[] args) {
        TrainingText[] texts = TrainingText.fetchTrainingTexts(trainingDataDir);
        String[] langs = Arrays.stream(texts).map(TrainingText::getLang).distinct().toArray(String[]::new);

        Layer layer = new Layer(langs);
        layer.train(texts);

        // PL
        System.out.println(layer.classify(new TrainingText("Komenda yes zwraca znak „y” lub dowolny ciąg znaków " +
                "podany jako argument, dopóki nie zostanie zatrzymana przez użytkownika lub zabita. Komenda po każdym " +
                "zwróconym ciągu znaków dodaje znak końca linii. Kiedy zostanie przekierowana jako część strumienia " +
                "będzie pracować dopóki strumień nie zostanie zakończony." +
                "Omawiana komenda może być także użyta do testowania jak system radzi sobie z wysokimi obciążeniami – " +
                "używanie yes skutkuje 100% zużyciem procesora dla systemów jednordzeniowych (dla systemów " +
                "wielordzeniowych, trzeba by uruchomić odpowiedni proces na każdym rdzeniu). To może być przydatne przy " +
                "sprawdzaniu czy chłodzenie komputera będzie wystarczające gdy procesor będzie pracował ze" +
                " 100% obciążeniem. ", null)));
        // EN
        System.out.println(layer.classify(new TrainingText("By itself, the yes command outputs 'y' or whatever is " +
                "specified as an argument, followed by a newline repeatedly until stopped by the user or otherwise " +
                "killed; when piped into a command, it will continue until the pipe breaks (i.e., the program " +
                "completes its execution). However, if the user enters a string after 'yes,' yes will output the " +
                "string the same as it would 'y,' similar to echo.\n" +
                "The version of yes bundled in GNU coreutils was written by David MacKenzie.[1]\n" +
                "The command is available as a separate package for Microsoft Windows as part of the GnuWin32 " +
                "project[2] and the UnxUtils collection of native Win32 ports of common GNU Unix-like utilities.[3] ", null)));
        // PT
        System.out.println(layer.classify(new TrainingText("No Linux, o comando yes imprime na tela infinitamente " +
                "a palavra yes; ele pode ser usado para executar comandos que exigem um número muito grande de " +
                "confirmações como, por exemplo, para sobrescrever vários arquivos.", null)));
        // EN
        System.out.println(layer.classify(new TrainingText("Limerence is a deep emotional state characterized by " +
                "an intense desire for a romantic connection with a particular person. Individuals experiencing " +
                "limerence are often obsessed with that person, constantly thinking about them, and feeling a strong " +
                "attraction towards them. This attraction can lead to a desire for intimacy and reciprocity in feelings.", null)));
        // PL
        System.out.println(layer.classify(new TrainingText("Rude koty od zawsze były obiektem zainteresowania i " +
                "fascynacji ze względu na swoją wyjątkową aparycję i zachowanie. Chociaż koty ogólnie znane są z " +
                "niezależności i chłodnego podejścia do ludzi, rude koty mogą wykazywać szczególnie interesujące cechy. " +
                "Jedną z nich jest ich skłonność do wyrażania emocji, często są przyjacielsko nastawione do ludzi i " +
                "lubią szukać uwagi i pieszczot. Ponadto badania wykazały, że rude koty często są wyjątkowo " +
                "inteligentne i potrafią szybko uczyć się nowych sztuczek lub dostosowywać się do nowych sytuacji.", null)));
    }
}
