FROM php:8.2-cli

# Install dependencies
RUN apt-get update && apt-get install -y \
    libpq-dev \
    libzip-dev \
    unzip \
    git \
    && docker-php-ext-install pdo_pgsql zip

# Set working directory
WORKDIR /var/www/html

# Copy only composer files first for caching
COPY composer.json composer.lock ./

# Install Composer
COPY --from=composer:2.8.3 /usr/bin/composer /usr/bin/composer

# Copy application code
COPY . .
RUN composer install --no-dev --optimize-autoloader

# Set permissions
RUN chown -R www-data:www-data /var/www/html/storage /var/www/html/bootstrap/cache \
    && chmod -R 775 /var/www/html/storage /var/www/html/bootstrap/cache

# Expose port
EXPOSE 8000

# Command to run Laravel
CMD ["php", "artisan", "serve", "--host=0.0.0.0", "--port=8000"]
