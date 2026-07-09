# DrunVisual (full source — non-fixed branch) — master context

**Путь:** `C:\Users\xwezent\Desktop\divinity\new\out\пульс сурс не фулл фикс`
**Сборка:** Gradle / Fabric mod / Minecraft 1.21.4 / Java 21
**Имя клиента (DrunVisual.CLIENT_NAME):** `drunvisual`
**Назначение:** клиентский чит-мод DrunVisual, recompilable source, рабочий, но с известными UI-багами после декомпиляции.

## Структура src/main/java

```
aethereal/                  — внутренняя утилити (вряд ли трогать)
com/wmedia/                 — auth/network
meteordevelopment/orbit/    — event bus (как в Meteor Client)
drunvisual/
  animation/                — AnimationState, Easing
  audio/, auth/, client/    — звук, авторизация, MC context
  config/                   — ConfigEntry, ConfigManager, LocalConfigManager, CloudConfigRepository
  core/                     — низкоуровневые утилки (Bool helper и пр.)
  cosmetic/, entity/        — модели/кейпы
  effects/                  — PotionEffectService (информация об активных зельях)
  events/                   — HudRenderPreEvent и т.д.
  gui/
    config/                 — Configs-таба (ConfigsTab, ConfigCard, ConfigListPanel, ConfigNameDialog, ConfigBoundsDialog, ConfigTextDialog, ConfigActionMenu)
    core/                   — каркас ClickGui (DrunVisualClickGuiScreen, TabSelector, TabHost, GuiInput, GuiInteractionState, PanelFadeOverlay)
    friends/                — FriendCard
    markers/                — MarkersTab
    modules/, notifications, settings, widgets
  hud/
    core/                   — HudElement, HudIcons, HudService, HudServiceRegistry
    elements/               — конкретные элементы худа (Potions/Cooldowns/Hotkeys/Inventory/Target/Saturation/Watermark)
    snap/                   — snap-точки для drag-and-drop
  inventory/                — CooldownInfo
  markers/                  — MapMarker, MapMarkerModule, MapMarkerRenderer, MarkerManager
  module/                   — ClientModule, ModuleRegistry
  modules/
    hud/                    — модули-обёртки HUD (PotionsHud, CooldownsHud и т.д.)
    utilities/, visuals/    — функциональные модули
  render/
    icons/                  — IconTextureRegistry, IconGlyphs (font glyph)
    font/, shader/, world/  — рендеринг
  theme/                    — Theme (цвета)
  util/                     — ColorUtils и проч.
ru/drunvisual/                   — DrunVisual main class + mixin/accessor
```

## Ключевые файлы и контракты

**HudElement** (`drunvisual.hud.core.HudElement`)
- абстрактный родитель всех элементов худа
- protected `void a()` — пересчёт размеров (this.d = width, this.e = height)
- public `void a(MatrixStack, Renderer2D, mouseX, mouseY)` — рендер
- `g()` — scale factor (float)
- `this.b`, `this.c` — позиция X, Y
- `this.d`, `this.e` — размеры (вычисляются в a())

**Renderer2D** (`drunvisual.render.Renderer2D`)
- `a(...)` overloads: прямоугольники, текстуры, градиенты
- `b()` — scissor / FBO push (возвращает builder с `.a(x, y, w, h, ..., matrix)` для push и `.a(matrix)` для pop)
- `c(x, y, w, h, matrix)` — старт FBO snapshot (для fade-in)
- `a(alpha, matrix)` — финальный fade flush

**HudIcons** (`drunvisual.hud.core.HudIcons`)
- `draw(...)` — обычная отрисовка
- `drawRotated180(...)` — поворот на 180° через scale(-1,-1,1). **ЗЕРКАЛИТ** изображение.
- `drawFlippedVertical(...)` — V-флип через UV

**IconTextureRegistry** (`drunvisual.render.icons.IconTextureRegistry`)
- `get(key)` — Identifier текстуры. Fallback на `textures/icon.png` если ключ не найден.
- `getInfo(key)` — TextureInfo (identifier + width + height).
- **ВАЖНО:** `registerPotionAliases()` алиасит ВСЕ vanilla effect-ключи (speed, strength, …) на одну и ту же `clickgui/potions.png` — это header-иконка. **Не использовать этот registry для рендера vanilla эффектов в entries.** Брать vanilla напрямую: `Identifier.of("minecraft", "textures/mob_effect/" + path + ".png")`.

**ConfigListPanel** (`drunvisual.gui.config.ConfigListPanel`)
- два разных метода `a(MatrixStack, Renderer2D, ...)`:
  - **с 9 аргументами** — рендер main panel (карточки конфигов + scrollbar)
  - **с 6 аргументами** — рендер **overlay-диалогов** (`l, m, n, o` — ActionMenu, NameDialog, BoundsDialog, TextDialog)
- `g()` — открыть NameDialog (add)
- `h()` — закрыть все диалоги
- `e()` — есть ли открытый диалог
- любая таба, использующая ConfigListPanel, **ДОЛЖНА** вызывать оба метода рендера — иначе диалоги невидимы, хотя state открыт.

**ConfigsTab.a(MatrixStack, Renderer2D, ...)** — рендер табы. Кнопки 3 шт. (animations o/p/q):
- leftmost → `m()` → `this.m.g()` — open NameDialog (add)
- middle  → `l()` → `this.m.h()` — close-all (текущий handler сомнительный, возможно baked-in temp; чинить позже)
- rightmost → `k()` → `h()` — refresh list

**MapMarkerRenderer** (`drunvisual.markers.MapMarkerRenderer`) — render маркеров в мире
- `a(HudRenderPreEvent)` — главный hook; берёт MarkerManager.a(), сортирует по distance descending, рисует.
- координаты screen берутся из `WorldToScreen.a(Vec3d)`.
- `fA4 = fA2 - <offset>` — Y-смещение текста-плашки. Большой offset → плашка выше точки в мире.

**HudElement subclasses (PotionsHud / HotkeysHud / CooldownsHud)** имеют сходную структуру:
- `a()` (update): пересчёт height/width, animations
- `a(MatrixStack, Renderer2D, ...)` (render)
- preview-mode когда `currentScreen instanceof ChatScreen` — рисуется placeholder (chosen из пула `this.U` / `this.O` / `this.P`)
- **ИЗВЕСТНАЯ ОСОБЕННОСТЬ:** в формуле height в Potions/Hotkeys в конец baseline вписаны `(I*fG) + (13.0f*fG)` (~15px) как зарезервированный слот под одну строку placeholder'а. В preview placeholder рисуется в этом резерве (i5=0). В runtime каждая активная строка добавляется к высоте, но резерв не вычитается → overcount на 1 строку.

## Известные баги (вызов erafox 2026-06-23)

### Bug 1 — метки в мире слишком высоко
- **где:** `drunvisual/markers/MapMarkerRenderer.java`, строка `fA4 = a(fA2 - 11.0f)` (вверх 11px)
- **fix:** `-11.0f` → `-3.0f` или ниже

### Bug 2 — кнопки в Configs-табе: затемняются (hover), но окно не открывается; после reopen GUI окно появляется
- **где:** `drunvisual/gui/config/ConfigsTab.java`, метод `a(MatrixStack, Renderer2D, float, float, int, int)`
- **причина:** не вызывается overlay-метод `ConfigListPanel.a(MatrixStack, Renderer2D, x, y, mouseX, mouseY)` (тот что с 6 аргументами рисует диалоги).
- **fix:** в конце render-метода ConfigsTab добавить вызов overlay-метода с center-screen anchor.

### Bug 3 — реальный HUD выше предпросмотра на одну строку
- **где:** `drunvisual/hud/elements/PotionsHudElement.java` (formula `if (i5 != 0) { f = i5 * f7 + (i5-1) * f8; }`) и `HotkeysHudElement.java` (аналогичная formula `i2 * f5 + (i2-1) * f6`).
- **причина:** baseline уже содержит `(I*fG) + (13*fG)` = резерв одной строки. Каждая активная строка дополнительно прибавляется → overcount.
- **fix:** `f = (i5 > 0) ? (i5-1) * (f7+f8) : 0;` и аналог для potions-section/hotkeys. Резерв в baseline остаётся (placeholder и первая строка живут в нём).

### Bug 4 — Potions HUD: header-иконка зеркалит влево, entries рисуются header-иконкой вместо vanilla эффектов
- **где (header):** `PotionsHudElement.drawClickGuiIcon(...)` → вызывает `HudIcons.drawRotated180` — это **зеркало через scale(-1,-1,1)**.
  - **fix:** заменить вызов на `HudIcons.draw(...)`.
- **где (entries):** `PotionsHudElement.b(RegistryEntry<StatusEffect>)` → `IconTextureRegistry.get(effectKey)`. Все vanilla effect-ключи алиасены на header-текстуру (см. `registerPotionAliases`).
  - **fix:** в `b(...)` собрать vanilla identifier напрямую: `Identifier.of(id.getNamespace(), "textures/mob_effect/" + id.getPath() + ".png")`.

### Bug 5 — CooldownsHud: spacing entries 12px (надо 14 как у Potions/Hotkeys) + затемнение всего худа при появлении кулдауна
- **где (spacing):** `CooldownsHudElement.a(MatrixStack, ...)`, строка `f12 += f5;` где f5=12*fG. Inter-row padding отсутствует.
- **где (height calc):** `CooldownsHudElement.a()`, цикл `f5 += f3;` — то же без padding.
- **fix spacing:** добавить inter-row padding 2*fG (как `f10` у Potions); НЕ к первой строке, к каждой последующей.
- **где (затемнение):** `Math.max(0.15f, activeCooldownEntry.f.j())` — форсит min alpha 0.15 для fade-in entry. При первом тике alpha 0.15 + `DrawContext.drawItem` + `setShaderColor(1,1,1,0.15)` = тёмная вспышка на весь HUD.
- **fix:** убрать `Math.max(0.15f, ...)`, использовать чистый animation value.

## Сборка / запуск

`./gradlew build` — собирает Fabric mod jar в `build/libs/`.
Запуск — через лончер divinity / drunvisual — см. divinity-build-run memory.

## Чего НЕ делать
- Не комментировать .java файлы (no-comments rule, anti-AI-tell).
- Не переименовывать поля массово — accessor-mapping уже сделан в v21.x decompile.
- Не трогать `aethereal/`, `com/wmedia/` без необходимости.
- Не использовать `IconTextureRegistry` для vanilla minecraft текстур.
