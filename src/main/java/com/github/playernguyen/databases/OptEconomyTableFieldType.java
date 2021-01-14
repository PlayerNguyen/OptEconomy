package com.github.playernguyen.databases;

public interface OptEconomyTableFieldType {

    /**
     * The declaration of the field type. For example with MySQL:<br>
     *  <i>VARCHAR, INTEGER, ...</i>
     *
     * @return the declaration of the field.
     */
    String getDeclaration();

}
