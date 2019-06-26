package pl.konradgugala.ordertaxi.business.util;

import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private DictionaryRule dictionaryRule;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        try {
            String invalidPasswordList = this.getClass().getResource("/invalid-password-list.txt").getFile();
            dictionaryRule = new DictionaryRule(
                    new WordListDictionary(WordLists.createFromReader(
                            // Sprawdzanie czy haslo jest na liscie niepoprawnych
                            new FileReader[]{
                                    new FileReader(invalidPasswordList)
                            },
                            // True dla sprawdzania wielkosci liter
                            false,
                            // sortowanie słownika
                            new ArraysSort()
                    )));
        } catch (IOException e) {
            throw new RuntimeException("nie udalo się załadować listy slow", e);
        }
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(

                // przynajmniej 8 znaków
                new LengthRule(8, 100),

                // przynajmniej jedna wielka litera
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // przynajmniej jedna mała litera
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // przynajmniej jedna cyfra
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // przynajmniej jeden znak specjalny
                new CharacterRule(EnglishCharacterData.Special, 1),

                // bez pustych znaków
                new WhitespaceRule(),

                // popularne hasla nie dozwolone
                dictionaryRule
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = messages.stream().collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}