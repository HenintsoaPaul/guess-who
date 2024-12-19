@extends('layout')

@section('content')
    <div class="login-container">
        <h2>Connexion</h2>
        <form action="{{ route('login.submit') }}" method="POST">
            @csrf
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="{{ old('email') }}" required>
                @error('email')
                    <div class="error">{{ $message }}</div>
                @enderror
            </div>
            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input type="password" id="password" name="password" required>
                @error('password')
                    <div class="error">{{ $message }}</div>
                @enderror
            </div>
            <button type="submit" class="btn">Se connecter</button>

            @if(session('errors'))
                <div class="error">{{ session('errors')->first() }}</div>
            @endif
        </form>
    </div>
@endsection

@section('styles')
    <link rel="stylesheet" href="{{ asset('css/login.css') }}">
@endsection
