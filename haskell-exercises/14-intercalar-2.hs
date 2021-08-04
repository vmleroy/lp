-- 14)
-- Dadas duas listas ordenadas de forma crescente, obter a lista ordenada resultante da intercalação delas.
-- Ex.: > intercalar [1,5,10] [2,7,9,20,25]
-- [1,2,5,7,9,10,20,25]

qsort :: [Int] -> [Int]
qsort [] = []
qsort (a:x) = qsort [ b | b <- x, b <= a ] ++ [a] ++ qsort [ b | b <- x, b > a ]

intercalar :: [Int] -> [Int] -> [Int]
intercalar [] [] = []
intercalar l  [] = l
intercalar [] l  = l
intercalar l k = qsort(l ++ k)
