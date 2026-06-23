# nepali-number-converter

A Java library for converting English numbers to Nepali (Devanagari) digits, words, and formatted text with Nepali/Indian comma system (lakh/crore), plus AD-to-BS (Bikram Sambat) date conversion.

## Features

- **English digits → Nepali digits** (e.g. `123` → `१२३`)
- **English numbers → Nepali words** (e.g. `1234` → `एक हजार दुई सय चौँतीस`)
- **Nepali comma formatting** (e.g. `12345678` → `१,२३,४५,६७८`)
- **Nepali digits → English digits** (e.g. `१२३` → `123`)
- **AD (Gregorian) ↔ BS (Bikram Sambat)** date conversion

## Usage

### Maven

```xml
<dependency>
  <groupId>io.github.sandeshkhatiwada05</groupId>
  <artifactId>nepali-number-converter</artifactId>
  <version>1.0.3</version>
</dependency>
```

*Check [Maven Central](https://central.sonatype.com/artifact/io.github.sandeshkhatiwada05/nepali-number-converter) for the latest version.*

### Quick Start

```java
import io.github.sandeshkhatiwada05.englishnepaliconversion.englishnepali.ConvertEnglishNepali;
import io.github.sandeshkhatiwada05.englishnepaliconversion.englishnepali.ConvertNepaliEnglish;
import io.github.sandeshkhatiwada05.englishnepaliconversion.adandbs.service.ConvertAdAndBs;

// English digits to Nepali digits
ConvertEnglishNepali.englishNumberToNepali(12345);     // "१२३४५"

// English number to Nepali words
ConvertEnglishNepali.englishNumberToNepaliSentence(1234);
// "एक हजार दुई सय चौँतीस"

// Nepali/Indian comma formatting
ConvertEnglishNepali.addNepaliCommas(12345678);        // "१,२३,४५,६७८"

// Nepali digits to English digits
ConvertNepaliEnglish.NepaliNumberToEnglish("१२३");    // "123"

// AD to BS
ConvertAdAndBs.adToBs(LocalDate.of(2025, 1, 14));     // NepaliDate(year=2081, month=10, day=1)

// BS to AD
ConvertAdAndBs.bsToAd(2081, 10, 1);                   // 2025-01-14
```

## Requirements

- Java 17+

## License

[MIT](LICENSE.md)
