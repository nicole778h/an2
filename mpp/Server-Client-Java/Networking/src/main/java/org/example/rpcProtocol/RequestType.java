package org.example.rpcProtocol;

public enum RequestType {
    FIND_USER,
    FIND_SPECTACOLE,
    LOGIN,
    LOGOUT,
    FIND_BILETE, // Adaugă tipul cererii pentru a găsi bilete
    SAVE_BILET,// Adaugă tipul cererii pentru a salva un bilet
    UPDATE_BILET,
     BUY_BILETE,
    DELETE_BILET,

    FIND_UTILIZATOR_BY_USERNAME,
    FIND_SPECTACOLE_BY_DATE;
  //  UPDATE_SPECTACOL;
}
