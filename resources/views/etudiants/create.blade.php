@extends('layout')

@section('content')
    <h1>Ajouter un étudiant</h1>
    <a href="{{ route('etudiants.index') }}">Ajouter un étudiant</a>
    <form action="{{ route('etudiants.store') }}" method="POST">
        @csrf
        <label>Nom : <input type="text" name="nom" value="{{ old('nom') }}"></label><br>
        <label>Prénom : <input type="text" name="prenom" value="{{ old('prenom') }}"></label><br>
        <label>Âge : <input type="number" name="age" value="{{ old('age') }}"></label><br>
        <button type="submit">Ajouter</button>
    </form>
@endsection
