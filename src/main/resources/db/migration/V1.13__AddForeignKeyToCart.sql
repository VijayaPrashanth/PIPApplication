ALTER TABLE CART ADD CONSTRAINT FK_INVENTORY FOREIGN KEY (INVENTORY_ID) REFERENCES INVENTORY(ID) ON DELETE CASCADE;