/*
"Dada uma lista de inteiros, determinar se existem e quais são os buracos dessa sequência de valores."
? game([3, 1, 4, 6, 5], X).
X = [2]
? game([3, 1, 2, 4], X).
X = []
? game([2, 6, 7, 5], X).
X = [3, 4]
*/

maior([N], N).
maior([N|L], MAIOR) :- maior(L, MAIOR), MAIOR > N.
maior([N|L], N) :- maior(L, MAIOR), N > MAIOR.

menor([N], N).
menor([N|L], MENOR) :- menor(L, MENOR), MENOR < N.
menor([N|L], N) :- menor(L, MENOR), N < MENOR.

gerar(FINAL, FINAL, [FINAL]).
gerar(PRIMEIRO, FINAL, [PRIMEIRO|L]) :- N is PRIMEIRO + 1, gerar(N, FINAL, L).

remover_elemento(_, [], []).
remover_elemento(N1, [N1|L], LR) :- remover_elemento(N1, L, LR).
remover_elemento(N1, [N2|L], [N2|LR]) :- N1 \== N2, remover_elemento(N1, L, LR).

remover([],[],[]).
remover([], GERADO, L) :- L = GERADO.
remover([CABECA_LISTA|LISTA], GERADO, L) :- remover_elemento(CABECA_LISTA, GERADO, GERADO_RESULTADO), remover(LISTA, GERADO_RESULTADO, L).

game([],[]).
game(LISTA,L) :- menor(LISTA, MENOR), maior(LISTA, MAIOR), gerar(MENOR, MAIOR, GERADO), remover(LISTA, GERADO, L). 


