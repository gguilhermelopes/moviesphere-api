INSERT INTO genre(id, name, created_At) VALUES ('98999055-08f3-4888-9ed3-ed31c38ffbe6', 'Action', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('92e5e3a1-5267-4a7d-8c7d-2c5b7c67c0b5', 'Adventure', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('e3079a4d-3cb1-4e0e-8399-0eaf53d0e0f9', 'Animation', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('7f1f7f3c-4b6b-4b6b-991a-1a01b1b1c2c9', 'Comedy', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('8c4b49da-91f8-42c3-84bf-d5d1a62d0c1d', 'Crime', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('a2a4bc0c-4294-4e01-809b-cd7dce0f6d64', 'Documentary', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('ec075f4a-75bd-4ef4-bb7e-3f80d10a9c11', 'Drama', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('f5062d5a-43f4-4b9b-8232-5616b1bd4c95', 'Family', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('fb5319b3-3445-4c64-b9f0-72649317e510', 'Fantasy', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('1d3c6ec4-2ba6-4a7a-9257-78a4d9e62d07', 'History', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('712f71cd-bb10-470a-a9a8-650e5c28fe5b', 'Horror', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('4a982995-01d2-4084-9d39-3c508b173174', 'Musical', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('e4c1d8de-55a2-4f04-9e23-69e428cc5e5e', 'Mystery', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('9dbef1b5-1b2d-42ae-b98e-228607f899fb', 'Romance', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('b9a843b7-37e1-4d68-88e9-56aaee16a7ae', 'Science Fiction', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('5c0e5a1d-0cb9-4cf7-95e1-3949de646b54', 'Thriller', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('85a0d380-0595-478b-9d61-2e4c0b6475b9', 'War', NOW());
INSERT INTO genre(id, name, created_At) VALUES ('b0c143c2-50a4-4eb9-b0c8-394aa6c3c9d1', 'Western', NOW());

INSERT INTO movie(id, name, release_Year, director, synopsis, img_Url, rating) VALUES('91c01ffe-28e0-4c3d-a904-b9c9d5b55877','The Godfather', 1972,'Francis Ford Coppola','Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.','https://a.ltrbxd.com/resized/film-poster/5/1/8/1/8/51818-the-godfather-0-1000-0-1500-crop.jpg?v=bca8b67402', 4.6);
INSERT INTO movie(id, name, release_Year, director, synopsis, img_Url, rating) VALUES('5eba0494-22a0-4a47-85df-25806d7dc4a6','Dune: Part Two', 2024,'Denis Villeneuve','Follow the mythic journey of Paul Atreides as he unites with Chani and the Fremen while on a path of revenge against the conspirators who destroyed his family. Facing a choice between the love of his life and the fate of the known universe, Paul endeavors to prevent a terrible future only he can foresee.','https://a.ltrbxd.com/resized/film-poster/6/1/7/4/4/3/617443-dune-part-two-0-1000-0-1500-crop.jpg?v=cc533700f8', 4.5);

INSERT INTO movie_genre (movie_id, genre_id) VALUES ('91c01ffe-28e0-4c3d-a904-b9c9d5b55877', 'ec075f4a-75bd-4ef4-bb7e-3f80d10a9c11');
INSERT INTO movie_genre (movie_id, genre_id) VALUES ('91c01ffe-28e0-4c3d-a904-b9c9d5b55877', '8c4b49da-91f8-42c3-84bf-d5d1a62d0c1d');
INSERT INTO movie_genre (movie_id, genre_id) VALUES ('5eba0494-22a0-4a47-85df-25806d7dc4a6', 'ec075f4a-75bd-4ef4-bb7e-3f80d10a9c11');
INSERT INTO movie_genre (movie_id, genre_id) VALUES ('5eba0494-22a0-4a47-85df-25806d7dc4a6', 'b9a843b7-37e1-4d68-88e9-56aaee16a7ae');
INSERT INTO movie_genre (movie_id, genre_id) VALUES ('5eba0494-22a0-4a47-85df-25806d7dc4a6', '92e5e3a1-5267-4a7d-8c7d-2c5b7c67c0b5');



