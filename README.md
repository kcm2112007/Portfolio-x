# PortfolioX

Native Android portfolio app. Kotlin + Jetpack Compose + MVVM + Hilt + Supabase.

## What's implemented

- **Project scaffold**: full Gradle setup (Compose, Hilt, Room, Coil, Ktor/Supabase-kt, DataStore)
- **Architecture**: MVVM + Repository pattern, clean folder structure (`core/`, `data/`, `domain/`, `di/`, `navigation/`, `features/`)
- **Supabase integration**: `SupabaseModule` wires Auth, Postgrest, Storage, Realtime — this is the Firebase replacement
- **Navigation**: full route graph for all 17 screens from the spec
- **Fully built screens**: Splash (logo animation + auto-login via Supabase session restore), Home (profile from Supabase)
- **Data models**: `Models.kt` — User, Skill, Project, Certificate, GalleryItem, Achievement, Education, BlogPost, all matching a Postgres schema

## Not yet implemented

Everything else is stubbed with `PlaceholderScreen` in `PortfolioXNavGraph.kt`: About, Skills, Projects (+ details), Certificates, Gallery, Blog, Contact, Settings, Admin Dashboard, Add/Edit Project, Upload Certificate, Upload Gallery, Profile Editor, Login.

Ask me to build any of these next — each one follows the same pattern as Home: a `Repository` hitting a Supabase table, a `ViewModel` exposing `StateFlow<UiState>`, and a Compose screen.

## Setup

1. **Create a Supabase project** at supabase.com (free tier is enough to start).
2. **Run the schema** below in the Supabase SQL editor to create your tables.
3. Copy `local.properties.example` → `local.properties`, fill in `sdk.dir` (your Android SDK path) and your project's URL + anon key from Supabase Settings → API.
4. Open the project root in Android Studio (Koala or newer), let Gradle sync, run on a device/emulator (minSdk 24 / Android 7+).
5. For **admin login**: in Supabase Dashboard → Authentication → Users, manually create your own admin user (email + password). The app's Login screen (once built) signs in with `AuthRepository.signIn`.
6. For **image/file uploads** (photos, certificates, resume PDF): create Storage buckets in Supabase (e.g. `avatars`, `certificates`, `gallery`, `resumes`) and set bucket policies — public read, authenticated write.

## Supabase schema (run in SQL editor)

```sql
create table profiles (
  id uuid primary key default gen_random_uuid(),
  name text not null,
  bio text,
  photo_url text,
  email text,
  phone text,
  resume_url text,
  location text,
  career_objective text,
  social_links jsonb default '{}'::jsonb
);

create table skills (
  id uuid primary key default gen_random_uuid(),
  name text not null,
  icon text,
  level int check (level between 0 and 100),
  category text not null,
  years_experience numeric default 0
);

create table projects (
  id uuid primary key default gen_random_uuid(),
  title text not null,
  description text not null,
  thumbnail text,
  images text[] default '{}',
  technologies text[] default '{}',
  github_url text,
  demo_url text,
  status text default 'COMPLETED',
  date date,
  category text
);

create table certificates (
  id uuid primary key default gen_random_uuid(),
  title text not null,
  issuer text not null,
  image text,
  pdf_url text,
  verification_url text,
  credential_id text,
  date date
);

create table gallery_items (
  id uuid primary key default gen_random_uuid(),
  type text not null, -- PHOTO | VIDEO
  title text,
  media_url text not null,
  album text,
  description text,
  date date
);

create table achievements (
  id uuid primary key default gen_random_uuid(),
  title text not null,
  description text,
  icon text,
  date date
);

create table education_entries (
  id uuid primary key default gen_random_uuid(),
  institution text not null,
  degree text not null,
  score text,
  start_date date,
  end_date date
);

create table blog_posts (
  id uuid primary key default gen_random_uuid(),
  title text not null,
  content text not null,
  cover_image text,
  category text,
  date date default now(),
  published boolean default true
);

-- Public read for the portfolio-visitor side, writes restricted to your admin user.
alter table profiles enable row level security;
alter table skills enable row level security;
alter table projects enable row level security;
alter table certificates enable row level security;
alter table gallery_items enable row level security;
alter table achievements enable row level security;
alter table education_entries enable row level security;
alter table blog_posts enable row level security;

create policy "public read" on profiles for select using (true);
create policy "public read" on skills for select using (true);
create policy "public read" on projects for select using (true);
create policy "public read" on certificates for select using (true);
create policy "public read" on gallery_items for select using (true);
create policy "public read" on achievements for select using (true);
create policy "public read" on education_entries for select using (true);
create policy "public read" on blog_posts for select using (published = true);

create policy "admin write" on profiles for all using (auth.uid() is not null);
create policy "admin write" on skills for all using (auth.uid() is not null);
create policy "admin write" on projects for all using (auth.uid() is not null);
create policy "admin write" on certificates for all using (auth.uid() is not null);
create policy "admin write" on gallery_items for all using (auth.uid() is not null);
create policy "admin write" on achievements for all using (auth.uid() is not null);
create policy "admin write" on education_entries for all using (auth.uid() is not null);
create policy "admin write" on blog_posts for all using (auth.uid() is not null);
```

Since there's only one admin (you), `auth.uid() is not null` is enough — any logged-in user can write. Tighten later if needed.

## Suggested build order

1. Login screen (unlocks Admin Dashboard)
2. About, Skills, Education (simple read-only screens, same pattern as Home)
3. Projects + Project Details (list/detail navigation pattern)
4. Certificates, Gallery (image-heavy, uses Coil + Supabase Storage URLs)
5. Blog
6. Contact (form that could email you or write to a `messages` table)
7. Admin Dashboard + all Add/Edit/Upload screens (the CRUD layer — build last, reuses repositories already written for steps 2–6)
8. Settings (theme persistence via DataStore, currently theme resets on app restart)
