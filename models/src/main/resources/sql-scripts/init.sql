-- Insert Ponudniki
INSERT INTO ponudniki (ime, mesto, postnaSt, ulica) VALUES ('Dobra vila', 'Ljubljana', 1000, 'Celovška cesta 166');


-- Insert Ponudbe
INSERT INTO ponudbe (ime, cena) VALUES ('Iskreno', 12.20);
INSERT INTO ponudbe (ime, cena) VALUES ('Dobra vila', 13.50);
INSERT INTO ponudbe (ime, cena) VALUES ('Klasika', 12.30);


INSERT INTO ponudniki_ponudbe (ponudnikentity_id, ponudbe_id) VALUES (1, 1)
INSERT INTO ponudniki_ponudbe (ponudnikentity_id, ponudbe_id) VALUES (1, 2)
INSERT INTO ponudniki_ponudbe (ponudnikentity_id, ponudbe_id) VALUES (1, 3)