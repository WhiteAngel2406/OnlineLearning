import os

os.makedirs('src/main/resources/templates/marketing/slider', exist_ok=True)

list_html = """<!DOCTYPE html>
<html lang="vi" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Slider - Marketing</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-slate-50 flex h-screen overflow-hidden text-slate-900">
    <!-- Sidebar -->
    <aside class="w-64 bg-white border-r border-slate-200 flex flex-col h-full shrink-0">
        <div class="h-16 flex items-center px-6 border-b border-slate-200 font-bold text-lg tracking-tight">
            <i class="ph-fill ph-rocket-launch text-indigo-600 mr-2 text-2xl"></i> Marketing Hub
        </div>
        <nav class="flex-1 overflow-y-auto py-4 px-3 space-y-1">
            <a href="/marketing/dashboard" class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-slate-700 hover:bg-slate-100">
                <i class="ph ph-squares-four text-lg mr-3"></i> Dashboard
            </a>
            <a href="/marketing/sliders" class="flex items-center px-3 py-2 text-sm font-medium rounded-lg bg-indigo-50 text-indigo-700">
                <i class="ph ph-image text-lg mr-3"></i> Quản lý Slider
            </a>
            <a href="/marketing/blogs" class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-slate-700 hover:bg-slate-100">
                <i class="ph ph-article text-lg mr-3"></i> Quản lý Bài viết
            </a>
        </nav>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 flex flex-col h-full overflow-hidden">
        <header class="h-16 bg-white border-b border-slate-200 flex items-center justify-between px-8 shrink-0">
            <h1 class="text-xl font-semibold text-slate-800">Đề xuất Slider</h1>
            <a href="/marketing/sliders/create" class="bg-indigo-600 text-white px-4 py-2 rounded-lg font-medium hover:bg-indigo-700 flex items-center">
                <i class="ph ph-plus mr-2"></i> Tạo Slider mới
            </a>
        </header>

        <div class="flex-1 overflow-y-auto p-8">
            <div th:if="${success}" class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4">
                <span class="block sm:inline" th:text="${success}"></span>
            </div>
            <div th:if="${error}" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4">
                <span class="block sm:inline" th:text="${error}"></span>
            </div>

            <div class="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
                <table class="min-w-full divide-y divide-slate-200">
                    <thead class="bg-slate-50">
                        <tr>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-slate-500 uppercase tracking-wider">Hình Ảnh</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-slate-500 uppercase tracking-wider">Tiêu Đề</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-slate-500 uppercase tracking-wider">Trạng Thái</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-slate-500 uppercase tracking-wider">Thao Tác</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-slate-200">
                        <tr th:if="${#lists.isEmpty(sliders.content)}">
                            <td colspan="4" class="px-6 py-12 text-center text-slate-500">Chưa có slider nào. Hãy tạo mới!</td>
                        </tr>
                        <tr th:each="slider : ${sliders.content}">
                            <td class="px-6 py-4 whitespace-nowrap">
                                <img th:if="${slider.imageUrl != null}" th:src="${slider.imageUrl}" class="h-16 w-16 object-cover rounded-lg border border-slate-200">
                                <div th:if="${slider.imageUrl == null}" class="h-16 w-16 bg-slate-100 rounded-lg flex items-center justify-center text-slate-400">
                                    <i class="ph ph-image text-2xl"></i>
                                </div>
                            </td>
                            <td class="px-6 py-4 whitespace-nowrap">
                                <div class="text-sm font-medium text-slate-900" th:text="${slider.title}">Tiêu đề</div>
                                <div class="text-sm text-slate-500" th:text="${slider.linkUrl}">Link</div>
                            </td>
                            <td class="px-6 py-4 whitespace-nowrap">
                                <span th:if="${slider.status == 'PENDING'}" class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800">Chờ duyệt</span>
                                <span th:if="${slider.status == 'SHOW'}" class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">Hiển thị</span>
                                <span th:if="${slider.status == 'HIDE'}" class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-slate-100 text-slate-800">Đã ẩn</span>
                            </td>
                            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                <form th:if="${slider.status == 'PENDING' or slider.status == 'HIDE'}" th:action="@{/marketing/sliders/delete/{id}(id=${slider.id})}" method="post" class="inline">
                                    <button type="submit" class="text-red-600 hover:text-red-900" onclick="return confirm('Xóa slider này?')"><i class="ph ph-trash text-lg"></i></button>
                                </form>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</body>
</html>
"""

create_html = """<!DOCTYPE html>
<html lang="vi" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tạo Slider - Marketing</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-slate-50 flex h-screen overflow-hidden text-slate-900">
    <!-- Sidebar -->
    <aside class="w-64 bg-white border-r border-slate-200 flex flex-col h-full shrink-0">
        <div class="h-16 flex items-center px-6 border-b border-slate-200 font-bold text-lg tracking-tight">
            <i class="ph-fill ph-rocket-launch text-indigo-600 mr-2 text-2xl"></i> Marketing Hub
        </div>
        <nav class="flex-1 overflow-y-auto py-4 px-3 space-y-1">
            <a href="/marketing/dashboard" class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-slate-700 hover:bg-slate-100">
                <i class="ph ph-squares-four text-lg mr-3"></i> Dashboard
            </a>
            <a href="/marketing/sliders" class="flex items-center px-3 py-2 text-sm font-medium rounded-lg bg-indigo-50 text-indigo-700">
                <i class="ph ph-image text-lg mr-3"></i> Quản lý Slider
            </a>
            <a href="/marketing/blogs" class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-slate-700 hover:bg-slate-100">
                <i class="ph ph-article text-lg mr-3"></i> Quản lý Bài viết
            </a>
        </nav>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 flex flex-col h-full overflow-hidden">
        <header class="h-16 bg-white border-b border-slate-200 flex items-center px-8 shrink-0">
            <a href="/marketing/sliders" class="text-slate-500 hover:text-slate-700 mr-4">
                <i class="ph ph-arrow-left text-xl"></i>
            </a>
            <h1 class="text-xl font-semibold text-slate-800">Đề xuất Slider Mới</h1>
        </header>

        <div class="flex-1 overflow-y-auto p-8">
            <div class="max-w-3xl mx-auto bg-white rounded-xl shadow-sm border border-slate-200 p-8">
                
                <div class="bg-blue-50 border-l-4 border-blue-500 p-4 mb-8 rounded-r-lg">
                    <div class="flex">
                        <div class="flex-shrink-0">
                            <i class="ph-fill ph-info text-blue-500 text-xl"></i>
                        </div>
                        <div class="ml-3">
                            <p class="text-sm text-blue-700">
                                Slider sau khi tạo sẽ được đánh dấu là <strong>Chờ duyệt</strong>. Quản trị viên (Admin) sẽ kiểm duyệt nội dung trước khi hiển thị công khai.
                            </p>
                        </div>
                    </div>
                </div>

                <form th:action="@{/marketing/sliders/create}" th:object="${slider}" method="post" enctype="multipart/form-data" class="space-y-6">
                    <div>
                        <label for="title" class="block text-sm font-medium text-slate-700 mb-1">Tiêu đề <span class="text-red-500">*</span></label>
                        <input type="text" id="title" th:field="*{title}" required class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition-colors">
                    </div>
                    
                    <div>
                        <label for="description" class="block text-sm font-medium text-slate-700 mb-1">Mô tả</label>
                        <textarea id="description" th:field="*{description}" rows="3" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition-colors"></textarea>
                    </div>

                    <div class="grid grid-cols-2 gap-6">
                        <div>
                            <label for="linkUrl" class="block text-sm font-medium text-slate-700 mb-1">Đường dẫn (URL)</label>
                            <input type="url" id="linkUrl" th:field="*{linkUrl}" placeholder="https://" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition-colors">
                        </div>
                        <div>
                            <label for="orderNumber" class="block text-sm font-medium text-slate-700 mb-1">Thứ tự hiển thị <span class="text-red-500">*</span></label>
                            <input type="number" id="orderNumber" th:field="*{orderNumber}" min="1" required placeholder="1" class="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition-colors">
                        </div>
                    </div>

                    <div>
                        <label for="imageFile" class="block text-sm font-medium text-slate-700 mb-1">Hình ảnh <span class="text-red-500">*</span></label>
                        <input type="file" id="imageFile" th:field="*{imageFile}" accept="image/*" required class="block w-full text-sm text-slate-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-indigo-50 file:text-indigo-700 hover:file:bg-indigo-100">
                    </div>

                    <div class="pt-4 flex justify-end gap-3 border-t border-slate-200">
                        <a href="/marketing/sliders" class="px-5 py-2 border border-slate-300 rounded-lg text-sm font-medium text-slate-700 hover:bg-slate-50">Hủy</a>
                        <button type="submit" class="px-5 py-2 bg-indigo-600 rounded-lg text-sm font-medium text-white hover:bg-indigo-700 shadow-sm">Gửi đề xuất</button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</body>
</html>
"""

with open('src/main/resources/templates/marketing/slider/list.html', 'w') as f:
    f.write(list_html)

with open('src/main/resources/templates/marketing/slider/create.html', 'w') as f:
    f.write(create_html)

