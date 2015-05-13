package bsaDb.client.customComponents;

import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Malloch on 6/29/14
 */
public class JPasswordFieldDefaultText extends JPasswordField {
    private Color defaultColor;
    private Color activeColor;
    private String defaultText;

    {
        defaultColor = Color.GRAY;
        activeColor = Color.BLACK;
        defaultText = "";
    }

    public JPasswordFieldDefaultText() {
        super();

        setDefaultValues();

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getForeground().equals(defaultColor)) {
                    setEchoChar('\u2022');
                    setText("");
                    setForeground(activeColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Util.isEmpty(getText())) {
                    setDefaultValues();
                }
            }
        });

    }

    private void setDefaultValues() {
        setForeground(defaultColor);
        setEchoChar((char) 0);
        setText(defaultText);
    }

    /**
     * Get the text color when default info is provided.
     * @return default color
     */
    public Color getDefaultColor() {
        return defaultColor;
    }

    /**
     * Set the text color for the field when their isn't any user input in the field.
     * @param defaultColor text color
     */
    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    /**
     * Get the text color when there is user input provided.
     * @return default color
     */
    public Color getActiveColor() {
        return activeColor;
    }

    /**
     * Sets the text color for when users have entered info.
     * @param activeColor text color.
     */
    public void setActiveColor(Color activeColor) {
        this.activeColor = activeColor;
    }

    /**
     * gets the message that is set when there is no user input.
     * @return default text
     */
    public String getDefaultText() {
        return defaultText;
    }

    /**
     * Sets the default message for when there is no user input in the field.
     * @param defaultText default message
     */
    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
        setText(defaultText);
    }

    public boolean isMessageDefault() {
        return getForeground().equals(defaultColor);
    }

    public void setDefault() {
        setDefaultValues();
    }

    public void loadText(String password) {
        setEchoChar('\u2022');
        setText(password);
        setForeground(activeColor);
    }
}
