Consignes de rendu pour la version finalisée de votre back end :
sur la partie JPA :
MOR (les entités) fonctionnel ✅
contient au moins un héritage ✅
contient au moins une relation bidirectionel (mappedBy) ✅
sur les DAO :
une DAO par entité ✅
présence d'au moins une requête JPQL, une requête nommée, une criteria query ✅
la requete nommée est dans l'entity Event. Quant à la criteria query, elle est dans 
EventDAO
au moins une de ces DAO doit contenir quelques méthodes métier (pas juste CRUD) ✅
sur l'API Rest :
un controller par entité ✅
documentation openAPI complète sur au moins un controller ✅
au moins un de ces controllers doit contenir des endpoints métiers (pas juste CRUD) ✅
au moins un de ces controllers doit utiliser un DTO ✅
une documentation (README ou autre) explicitant votre modèle métier, le diagramme de classe associé, les endpoits que votre backend mets à disposition ✅