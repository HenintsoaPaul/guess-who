@extends('layout')

@section('content')
    <h1>Modifier l'étudiant</h1>
    <form action="{{ route('etudiants.update', $etudiant) }}" method="POST">
        @csrf
        @method('PUT')
        <label>Nom : <input type="text" name="nom" value="{{ $etudiant->nom }}"></label><br>
        <label>Prénom : <input type="text" name="prenom" value="{{ $etudiant->prenom }}"></label><br>
        <label>Âge : <input type="number" name="age" value="{{ $etudiant->age }}"></label><br>
        <button type="submit">Mettre à jour</button>
    </form>
@endsection
