import java.util.ArrayList;
import java.util.List;

// Trade off between SRP and Principle of least knowledge

abstract class DocumentElement {
    abstract String render();
}

class TextElement extends DocumentElement {
    private String text;
    public TextElement(String text) {
        this.text = text;
    }
    @Override
    String render() {
        return text;
    }
}

class ImageElement extends DocumentElement {
    private String path;
    public ImageElement(String path) {
        this.path = path;
    }
    @Override
    String render() {
        return "Image[" + path + "]";
    }
}

class NewLineElement extends DocumentElement {
    @Override
    String render() {
        return "\n";
    }
}

class Document {
    private List<DocumentElement> elements;

    Document(List<DocumentElement> elements) {
        this.elements = (elements != null) ? elements : new ArrayList<>();
    }

    public void addElement(DocumentElement element) {
        elements.add(element);
    }

    String render() {
        StringBuilder result = new StringBuilder();
        for (DocumentElement element : elements) {
            result.append(element.render());
        }
        return result.toString();
    }
}

abstract class Persistence {
    abstract void save(String data);
}

class SaveToDB extends Persistence {
    @Override
    void save(String data) {
        System.out.println("Saved to DB: " + data);
    }
}

class DocumentEditor {
    private Document document;
    private Persistence persistence;
    private String renderedOutput;

    DocumentEditor(Document document, Persistence persistence) {
        this.document = document;
        this.persistence = persistence;
    }

    public void addText(String text) {
        document.addElement(new TextElement(text));
    }

    public void addImage(String path) {
        document.addElement(new ImageElement(path));
    }

    public void addNewLine() {
        document.addElement(new NewLineElement());
    }

    public String renderDocument() {
        if (renderedOutput == null) {
            renderedOutput = document.render();
        }
        return renderedOutput;
    }

    public void saveDocument() {
        persistence.save(renderDocument());
    }
}

public class root {
    public static void main(String[] args) {
        Document document = new Document(new ArrayList<>());
        Persistence persistence = new SaveToDB();

        DocumentEditor editor = new DocumentEditor(document, persistence);

        editor.addText("Hello World");
        editor.addNewLine();
        editor.addImage("new.png");

        System.out.println("Document:\n" + editor.renderDocument());
        editor.saveDocument();
    }
}
