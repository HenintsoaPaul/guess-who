@extends('layout')

@section('content')
    <h1>Liste des étudiants</h1>
    <a href="{{ route('etudiants.create') }}">Ajouter un étudiant</a>
    <!-- @if(session('success'))
        <p>{{ session('success') }}</p>
    @endif -->
    <ul>
        @foreach($etudiants as $etudiant)
            <li>
                {{ $etudiant->nom }} {{ $etudiant->prenom }}, {{ $etudiant->age }} ans
                <a href="{{ route('etudiants.edit', $etudiant) }}">Modifier</a>
                <form action="{{ route('etudiants.destroy', $etudiant) }}" method="POST" style="display:inline;">
                    @csrf
                    @method('DELETE')
                    <button type="submit">Supprimer</button>
                </form>
            </li>
        @endforeach
    </ul>
@endsection
