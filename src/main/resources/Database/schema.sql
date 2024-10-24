-- Indsæt byer
INSERT INTO City (name) VALUES ('COPENHAGEN'), ('FREDERIKSBERG');

-- Indsæt attraktioner
INSERT INTO Attraction (name, cityID, description, fee) VALUES
('Tivoli', 1, 'Forlystelsespark', 120),
('Københavns ZOO', 2, 'Zoologisk have', 150),
('Nationalmuseet', 1, 'Danmarks statlige kulturhistoriske hovedmuseum', 0);

-- Indsæt tags
INSERT INTO Tag (name) VALUES
('ENTERTAINMENT'), ('ROLLER_COASTER'), ('CANDY'),
('ANIMALS'), ('PLAYGROUND'), ('MUSEUM'),
('ANCIENT TIMES'), ('PAINTINGS'), ('CAFE'), ('WEDDINGS');

-- Forbindelserne mellem attraktioner og tags
INSERT INTO Attraction_tag (attraction_id, tag_id) VALUES
-- Tivoli
(1,1), (1,2), (1,3),
-- ZOO
(2,4), (2,5), (2,1),
-- Nationalmuseet
(3,6), (3,7), (3,8), (3,9), (3,10);