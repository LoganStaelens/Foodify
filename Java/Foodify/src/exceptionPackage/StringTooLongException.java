package exceptionPackage;

public class StringTooLongException extends Exception {
    private String fieldName;
    private int length;
    private int maxLength;

    public StringTooLongException(String fieldName, int length, int maxLength) {
        this.fieldName = fieldName;
        this.length = length;
        this.maxLength = maxLength;
    }

    public String getFieldName() {
        return fieldName;
    }

    public int getLength() {
        return length;
    }

    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(fieldName)
        .append(" est trop long, il contient ")
        .append(length + " characteres mais a une longueur maximum de ")
        .append(maxLength + "characteres.");
        return builder.toString();
    }
}
