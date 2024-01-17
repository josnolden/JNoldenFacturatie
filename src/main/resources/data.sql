INSERT INTO adres (huisnummer, adres_id, huisnummer_toevoeging, land, straat, woonplaats) VALUES (13, -1, null, 'België', 'Teststraat', 'Testdorp');

INSERT INTO klant (klant_adres_adres_id, klant_id, achternaam, mail_adres, telefoonnummer, tussenvoegsel, voornaam) VALUES (-1, -1, 'Testman', 'testklant@jnoldenfacturatie.nl', '06-12345678', null, 'Tester');

INSERT INTO artikel (btw_percentage, prijs, artikel_id, categorie, naam) VALUES (21, 13.13, -1, 'Diensten', 'TestArtikel');

INSERT INTO factuur (btw_totaal, korting, sub_totaal, totaal_prijs, betaal_datum, factuur_datum, factuur_id, factuur_klant_klant_id) VALUES (2.28, 1.97, 10.85, 11.16, '2024-01-16T01:00:00.000+00:00', '2024-01-16T01:00:00.000+00:00', -1, -1);

INSERT INTO order_regel (aantal, btw, prijs, regel_nummer, basis_factuur_factuur_id, order_artikel_artikel_id, order_regel_id) VALUES (1, 1.97, 13.00, 1, -1, -1, -1);