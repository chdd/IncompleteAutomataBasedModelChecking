package it.polimi.constraints.io.out.replacement;

import it.polimi.action.CHIAAction;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.constraints.components.Replacement;

import javax.xml.parsers.ParserConfigurationException;

import com.google.common.base.Preconditions;

/**
 * Transforms the replacement into the corresponding string representation
 * 
 * @author Claudio Menghi
 *
 */
public class ReplacementToStringTransformer extends CHIAAction<String> {
    /**
     * The name of the CHIAAction
     */
    private final static String NAME = "PRINT REPLACEMENT";

    /**
     * The replacement to be converted into the corresponding String
     */
    protected Replacement replacement;

    /**
     * Creates a new ReplacementToStringTransformer
     * 
     * @param replacement
     *            the replacement to be converted into the corresponding string
     * @throws NullPointerException
     *             if the replacement is null
     */
    public ReplacementToStringTransformer(Replacement replacement) {
        super(NAME);
        Preconditions.checkNotNull(replacement,
                "the replacement to be considered cannot be null");
        this.replacement = replacement;
    }

    /**
     * returns the string representation of the replacement
     * 
     * @return the string representation of the replacement
     */
    public String perform() throws ParserConfigurationException, Exception {
        return new ElementToStringTransformer()
                .transform(new ReplacementToElementTransformer()
                        .transform(replacement));
    }
}
