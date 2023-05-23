check(X, N) :-
	X * X =< N,
	N mod X =:= 0.

check(X, N) :-
	X * X =< N,
	X1 is X + 1,
	check(X1, N).

prime(N) :-
	N > 1,
	\+ check(2, N).

composite(N) :-
	N > 1,
	\+ prime(N).

square_divisors(N, D) :-
	N1 is (N * N),
	prime_divisors(N1, D).

prime_divisors(1, _, []).
prime_divisors(N, X, [X | Divisors]) :-
	X =< N,
	prime(X),
	N mod X =:= 0,
	N1 is N // X,
	prime_divisors(N1, X, Divisors).

prime_divisors(N, X, Divisors) :-
	X1 is X + 1,
	prime_divisors(N, X1, Divisors).

prime_divisors(N, Divisors) :-
	prime_divisors(N, 2, Divisors), !.