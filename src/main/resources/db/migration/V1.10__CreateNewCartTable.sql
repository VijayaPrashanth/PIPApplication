CREATE TABLE CART( ID INT GENERATED BY DEFAULT AS IDENTITY,
                          NAME VARCHAR(100) NOT NULL ,
                          QUANTITY INTEGER NOT NULL,
                          UNIT VARCHAR(100) NOT NULL,
                           PRIMARY KEY(ID));