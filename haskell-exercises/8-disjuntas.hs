-- 8)
-- Verificar se duas listas são disjuntas.
-- Ex.: > disjuntas [1,2,3] [5,4,6,0]
-- True

disjuntas :: [Integer] -> [Integer] -> Bool
disjuntas x [] = True
disjuntas [] y = True
disjuntas x (y:l)
 | igualNoArray y x = False
 | otherwise = disjuntas x l

igualNoArray :: Integer -> [Integer] -> Bool
igualNoArray x [] = False
igualNoArray x (y:l)
 | x == y = True
 | otherwise = igualNoArray x l

