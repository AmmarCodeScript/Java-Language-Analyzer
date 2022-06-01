
/** This enum class is a suggestion. It's free
 * to use it or not, or to change it
 * according to own needs.
 *
 * You can of course have a simpler enum (without name and constructor) with
 * only DE, EN, ES ... but in this way you can easily get the language
 * name printed if necessary!
 */
public enum LangLabel {

    DE("German"),
    EN("English"),
    ES("Spanish"),
    FI("Finnish"),
    FR("French"),
    IT("Italian"),
    NO("Norwegian"),
    SV("Swedish");


    private String name;

    // Special constructor run by the enum constant itself
    LangLabel(String name) {
        this.name = name;
    }

    // Getter for the name initialized in the constructor. For example:
    // LangLabel.SV.getName (); returns "Swedish"
    public String getName() {
        return name;
    }
}
