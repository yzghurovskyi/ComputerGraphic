package lab3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadingHeaderFromBitmapImage {
    public PrintingImage pr;

    public HeaderBitmapImage Reading (BufferedInputStream reader) throws IOException
// метод, який приймає потік даних зчитаних із файлу із зображенням
// та повертатиме об’єкт типу HeaderBitmapImage, з інформацією про зображення
    {
        HeaderBitmapImage hbi = new HeaderBitmapImage();
        int line;
        int i = 0;
        short type = 0;
        long size = 0;
        short res1 = 0;
        short res2 = 0;
        long offset = 0;
        long header = 0;
        long width = 0;
        long height = 0;
        short numbPanel = 0;
        short bitCount = 0;
        long compr = 0;
        long sCompIm = 0;
        long hRes = 0;
        long vRes = 0;
        long numbUCol = 0;
        long numbICol = 0;
        long half=0;
        long temp = 0;
        while ((line = reader.read()) != -1) { // поки не кінець файлу
            i++; // збільшуємо лічильник кількості байт заголовку зображення
            if (i == 1) // зчитуємо сигнатуру
            {
                temp = reader.read();
                type += (temp * 0x100) + line;
                i++;
            }
            if (i == 2) // якщо зміщення відносно початку файлу = 2, то зчитуємо розмір файлу
            {
                size = readLong(reader); // у змінну size записуємо результат роботи методу readLong
                i = i + 4; // додаємо 4 до кількості зчитаних байт з файлу, так як розмір поля size 4 байти
            }
            if (i == 6) //зчитуємо резервоване поле №1
            {
                res1 = readShort(reader); // у змінну res1 записуємо результат роботи методу readShort
                i = i + 2; // додаємо 2 до кількості зчитаних байт з файлу, так як розмір поля res1 2 байти
            }
            if (i == 8) //зчитуємо резервоване поле №2
            {
                res2 = readShort(reader);
                i = i + 2;
            }
            if (i == 10) //зчитуємо зміщення
            {
                offset = readLong(reader);
                i = i + 4;
            }
            if (i == 14) //зчитуємо розмір заголовку
            {
                header = readLong(reader);
                i = i + 4;
            }
// зчитуємо з 18ої та 22ої позиції ширину і довжину зображення
            if (i == 18)
            {
                width = readLong(reader);
                i = i + 4;
                height = readLong(reader);
                i = i + 4;
                half = width;
                if((half % 2) != 0) // перевірка чи ширина зображення кратна 2 і якщо ні, то збільшуємо це значення на 1
                    half++; // щоб доповнити значення половини від ширини зображення
                half /= 2;
                if((half % 4) != 0) // якщо не ділиться на 4
                    half = (half / 4) * 4 + 4; // доповнюємо значення половини ширини зображення, щоб вона була кратна 4
            }
            if (i == 26) //зчитуємо кількість площин
            {
                numbPanel = readShort(reader);
                i = i + 2;
            }
            if (i == 28) //зчитуємо кількість біт
            {
                bitCount = readShort(reader);
                i = i + 2;
            }
            if (i == 30) //зчитуємо тип ущільнення
            {
                compr = readLong(reader);
                i = i + 4;
            }
            if (i == 34) //зчитуємо розмір ущільненого зображення
            {
                sCompIm = readLong(reader);
                i = i + 4;
            }
            if (i == 38) // горизонтальна роздільна здатність
            {
                hRes = readLong(reader);
                i = i + 4;
            }
            if (i == 42) // вертикальна роздільна здатність
            {
                vRes = readLong(reader);
                i = i + 4;
            }
            if (i == 46) // кількість кольорів палітри
            {
                numbUCol = readLong(reader);
                i = i + 4;
            }
            if (i == 50) // кількість важливих кольорів
            {
                numbICol = readLong(reader);
                i = i + 4;
            }
// записуємо усі зчитані значення в об'єкт
            hbi.setValues(type, size, res1, res2, offset, header, width,
                    height, numbPanel, bitCount, compr, sCompIm, hRes,
                    vRes, numbUCol, numbICol, half);
// помічяємо місце в потоці, де починаються пікселі
            if (i == offset)
            {
                reader.mark(1);
                break;
            }
        }
// вертаємося на те місце звідки мають починатися пікселі
        reader.reset();
// запишемо в окремий файл частину зображення, з якої починаються власне пікселі
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream("pixels.txt"));
        while ((line = reader.read())!=-1)
        {
            writer.write(line);
        }
        writer.close();
        this.pr = new PrintingImage (hbi);
        return hbi;
    }

    private short readShort(BufferedInputStream reader) throws IOException {
        short valueToReturn = 0;
        for(long j = 0x1; j <= 0x1000; j *= 0x100) // цикл від 1 до 8 з кроком j*4 - відповідно кількість виконань циклу = 2
        {
            valueToReturn += (reader.read() * j); // додаємо до поточного числа значення нового розряду записаного у 10-вій системі числення
        }
        return valueToReturn;
    }

    private long readLong(BufferedInputStream reader) throws IOException {
        long valueToReturn = 0;
        for(long j = 0x1; j <= 0x1000000; j *= 0x100) // цикл від 1 до 64 з кроком j*4 - відповідно кількість виконань циклу = 4
        {
            valueToReturn += (reader.read() * j); // додаємо до поточного числа значення нового розряду записаного у 10-вій системі числення
        }
        return valueToReturn;
    }
}
